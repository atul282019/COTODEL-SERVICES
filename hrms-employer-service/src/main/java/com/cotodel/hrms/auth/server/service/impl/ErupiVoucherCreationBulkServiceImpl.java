package com.cotodel.hrms.auth.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherBulkDao;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiBulkIdRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkVoucherCreateRequest;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.service.ErupiVoucherCreationBulkService;
import com.cotodel.hrms.auth.server.service.ErupiVoucherInitiateDetailsService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Service
public class ErupiVoucherCreationBulkServiceImpl implements ErupiVoucherCreationBulkService{
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherCreationBulkServiceImpl.class);
	
	@Autowired
	ErupiVoucherBulkDao erupiVoucherBulkDao;
	
	@Autowired
	ErupiVoucherInitiateDetailsService erupiVoucherInitiateDetailsService;
	
	@Autowired
    private EntityManager entityManager;
	
	@Override
	public ErupiVoucherBulkUploadSFListResponse saveErupiVoucherBulkFile(
			ErupiVoucherBulkUploadRequest request) {

		VoucherBulkUploadEntity voucherBulkUploadEntity = new VoucherBulkUploadEntity();
		ErupiVoucherBulkUploadSFListResponse bulkupload = new ErupiVoucherBulkUploadSFListResponse();
		String response = "";
		try {

			response = MessageConstant.RESPONSE_FAILED;
			Long orgId = request.getOrgId();
			Long voucherId=request.getVoucherId();
			String filename = request.getFileName();
			String extn = CommonUtility.getFileExtension(filename);
			String fileNameWithout = filename.substring(0, filename.lastIndexOf("."));
			String uniqueFileName = CommonUtility.generateUniqueFileName(fileNameWithout, request.getOrgId(), extn);
			LocalDate eventDate = LocalDate.now();
			CopyUtility.copyProperties(request, voucherBulkUploadEntity);
			voucherBulkUploadEntity.setCreationDate(eventDate);
			voucherBulkUploadEntity.setFile(request.getFile());
			voucherBulkUploadEntity.setFileName(uniqueFileName);
			voucherBulkUploadEntity.setOrgId(orgId);
			//voucherBulkUploadEntity.setRedemtionType(re);
			voucherBulkUploadEntity = erupiVoucherBulkDao.saveDetails(voucherBulkUploadEntity);

			byte[] decodedBytes = request.getFile();
			// Convert byte[] to InputStream
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
			// Read Excel file using Apache POI
			XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(byteArrayInputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			boolean isHeaderRow = true;
			int totalCount = 0;
			int successCount = 0;
			int failCount = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (isHeaderRow) {
					// Process the header row
					String benName = row.getCell(1).getStringCellValue();
					String mobile = row.getCell(2).getStringCellValue();
					String amount = row.getCell(3).getStringCellValue();
					String starDate = row.getCell(4).getStringCellValue();
					String expDate = row.getCell(5).getStringCellValue();
					totalCount = 0;
					isHeaderRow = false;
				} else {
					// Process data row
					totalCount++;
					String voucherType = row.getCell(1).getStringCellValue();
					String benName = row.getCell(2).getStringCellValue();
					String mobile = "";
					if (row.getCell(3).getCellType() == CellType.STRING) {
						mobile = row.getCell(3).getStringCellValue();
					} else {
						mobile = String.valueOf((long) row.getCell(3).getNumericCellValue());
					}
					String amount = "";
					if (row.getCell(4).getCellType() == CellType.STRING) {
						amount = row.getCell(4).getStringCellValue();
					} else {
						amount = String.valueOf(row.getCell(4).getNumericCellValue());
					}
					Date starDate = row.getCell(5).getDateCellValue();
					LocalDate stDate = CommonUtility.convertToLocalDate(starDate);
					Date endDate = row.getCell(6).getDateCellValue();
					LocalDate etDate = CommonUtility.convertToLocalDate(endDate);
					//System.out.println(etDate);

					boolean mob = CommonUtility.isValid(mobile);
					boolean name = CommonUtility.isValidName(benName);
					
					boolean amountValid = CommonUtility.isValidAmount(amount);
					boolean expDateValid = CommonUtility.checkDate(etDate.toString());
					String message = "";
					message = mob == false ? "Invalid Mobile " : "";
					// message=message==""?""
					message += name == false ? "Invalid name" : "";
					message += expDateValid == false ? "Invalid Date" : "";
					
					if (mob && name && amountValid && expDateValid) {		
						
						saveSuccess(request,voucherBulkUploadEntity.getId(),uniqueFileName,voucherType,
								benName,mobile,amount,stDate,etDate,orgId,voucherId);
						successCount++;

					} else {
						VoucherBulkUploadFailEntity voucherBulkUploadFailEntity = new VoucherBulkUploadFailEntity();
						
						CopyUtility.copyProperties(request, voucherBulkUploadFailEntity);
						voucherBulkUploadFailEntity.setBulktblId(voucherBulkUploadEntity.getId());
						voucherBulkUploadFailEntity.setFileName(uniqueFileName);
						voucherBulkUploadFailEntity.setVoucherType(voucherType);
						voucherBulkUploadFailEntity.setRedemtionType(voucherType);
						voucherBulkUploadFailEntity.setBeneficiaryName(benName);
						voucherBulkUploadFailEntity.setMobile(mobile);
						voucherBulkUploadFailEntity.setAmount(amount);
						voucherBulkUploadFailEntity.setStartDate(stDate);
						voucherBulkUploadFailEntity.setExpDate(etDate);
						LocalDate curDate = LocalDate.now();
						voucherBulkUploadFailEntity.setCreationDate(curDate);
						voucherBulkUploadFailEntity.setMessage(message);
						voucherBulkUploadFailEntity.setOrgId(orgId);
						erupiVoucherBulkDao.saveFailDetails(voucherBulkUploadFailEntity);
						failCount++;
					}
				}

				
			}

			voucherBulkUploadEntity.setTotalCount(String.valueOf(totalCount));
			voucherBulkUploadEntity.setSuccessCount(String.valueOf(successCount));
			voucherBulkUploadEntity.setFailCount(String.valueOf(failCount));
			erupiVoucherBulkDao.saveDetails(voucherBulkUploadEntity);

			List<VoucherBulkUploadSuccessEntity> voucherSuccessList = erupiVoucherBulkDao.findSuccessList(orgId,
					uniqueFileName);
			List<VoucherBulkUploadFailEntity> voucherFailList = erupiVoucherBulkDao.findFailList(orgId, uniqueFileName);
			bulkupload.setTotalCount(String.valueOf(totalCount));
			bulkupload.setSuccessCount(String.valueOf(successCount));
			bulkupload.setFailCount(String.valueOf(failCount));
			bulkupload.setSuccess(voucherSuccessList);
			bulkupload.setFail(voucherFailList);
		} catch (Exception e) {
			e.printStackTrace();
			response = MessageConstant.RESPONSE_FAILED;
			logger.error("Error :: " + e.getMessage());

		}
		return bulkupload;
	}

	@Override
	public List<ErupiVoucherCreateDetailsRequest> createErupiVoucherBulkFile(
			ErupiVoucherBulkVoucherCreateRequest request) {
		VoucherBulkUploadSuccessEntity voEntity=new VoucherBulkUploadSuccessEntity();
		ErupiVoucherCreateDetailsRequest erRequest=new ErupiVoucherCreateDetailsRequest();
		List<ErupiVoucherCreateDetailsRequest> erupiList=new ArrayList<ErupiVoucherCreateDetailsRequest>();
		try {
	        List<String> idList = Arrays.asList(request.getArrayofid());
	        
			for (String id : idList) {
				
			Long idValue=Long.valueOf(id);
			
			voEntity=erupiVoucherBulkDao.findSuccessDetails(idValue);
			if(voEntity!=null) {
				CopyUtility.copyProperties(voEntity, erRequest);
				//CopyUtility.copyProperties(request, erRequest);
				VoucherTypeMasterEntity vtme=new VoucherTypeMasterEntity();
				vtme.setId(voEntity.getVoucherId());
				erRequest.setName(voEntity.getBeneficiaryName());
				erRequest.setAmount(Float.valueOf(voEntity.getAmount()));
				erRequest.setRedemtionType(request.getRedemtionType());
				erRequest.setBulktblId(idValue);
				erRequest.setVoucherId(vtme);
				//ErupiVoucherInitiateDetailsServiceImpl help=new ErupiVoucherInitiateDetailsServiceImpl();
				//String merTranId=help.getMerTranId(voEntity.getBankcode());
				erRequest=erupiVoucherInitiateDetailsService.saveErupiVoucherInitiateDetails(erRequest);
				//if(erRequest.getResponse().equalsIgnoreCase("SUCCESS")){
					erupiVoucherBulkDao.updateSuccessFlag(idValue);
				//}
			}
			erupiList.add(erRequest);
			}
		} catch (Exception e) {
			logger.error("Error :: " + e.getMessage());
		}
		
		return erupiList;
	}
	public long getMerchantTranId() {
        Query query = entityManager.createNativeQuery("SELECT nextval('merchanttranid')");
        return ((Number) query.getSingleResult()).longValue();
    }
    
    public String getMerTranId(String bankcode) {
    	bankcode=bankcode==null?"":bankcode;
    	Long value=getMerchantTranId();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
        String date = sdf.format(new Date());
        String uniqueId=bankcode+date+value;
        System.out.println(uniqueId);
    	return uniqueId;
    }

	@Override
	public int updateErupiVoucherStatus(ErupiBulkIdRequest eBulkIdRequest) {
		// TODO Auto-generated method stub
		int result=0;
		try {
			 result=erupiVoucherBulkDao.updateSuccessStatus(eBulkIdRequest.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	public void saveSuccess(ErupiVoucherBulkUploadRequest request,Long id,String uniqueFileName,
			String voucherType,String benName,String mobile,String amount,LocalDate stDate,LocalDate etDate,Long orgId,Long voucherId) {
		VoucherBulkUploadSuccessEntity voucherBulkUploadSuccessEntity = new VoucherBulkUploadSuccessEntity();
		CopyUtility.copyProperties(request, voucherBulkUploadSuccessEntity);
		voucherBulkUploadSuccessEntity.setBulktblId(id);
		
		voucherBulkUploadSuccessEntity.setFileName(uniqueFileName);
		voucherBulkUploadSuccessEntity.setVoucherType(voucherType);
		//voucherBulkUploadSuccessEntity.setRedemtionType("SINGLE");
		voucherBulkUploadSuccessEntity.setBeneficiaryName(benName);
		voucherBulkUploadSuccessEntity.setMobile(mobile);
		voucherBulkUploadSuccessEntity.setAmount(amount);
		voucherBulkUploadSuccessEntity.setStartDate(stDate);		
		voucherBulkUploadSuccessEntity.setExpDate(etDate);
		voucherBulkUploadSuccessEntity.setStatus(1l);
		LocalDate curDate = LocalDate.now();
		voucherBulkUploadSuccessEntity.setCreationDate(curDate);
		String benid=mobile+curDate.getDayOfMonth();
		voucherBulkUploadSuccessEntity.setBeneficiaryID(benid);
		voucherBulkUploadSuccessEntity.setOrgId(orgId);
		voucherBulkUploadSuccessEntity.setVoucherId(voucherId);
		erupiVoucherBulkDao.saveSuccessDetails(voucherBulkUploadSuccessEntity);
	}

}
