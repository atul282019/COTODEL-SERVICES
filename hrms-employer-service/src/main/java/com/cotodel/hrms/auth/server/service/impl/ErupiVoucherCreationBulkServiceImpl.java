package com.cotodel.hrms.auth.server.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherBulkDao;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiBulkIdRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherMasterUploadRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherMasterUploadSFResponse;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherMasterUploadEntity;
import com.cotodel.hrms.auth.server.model.master.MccMasterEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherCreationBulkService;
import com.cotodel.hrms.auth.server.service.ErupiVoucherInitiateDetailsService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.ValidateConstants;
@Service
public class ErupiVoucherCreationBulkServiceImpl implements ErupiVoucherCreationBulkService{
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherCreationBulkServiceImpl.class);
	
	@Autowired
	ErupiVoucherBulkDao erupiVoucherBulkDao;
	
	@Autowired
	ErupiVoucherInitiateDetailsService erupiVoucherInitiateDetailsService;
	
	@Autowired
    private EntityManager entityManager;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
//	@Override
//	public ErupiVoucherBulkUploadSFListResponse saveErupiVoucherBulkFile(
//			ErupiVoucherBulkUploadRequest request) {
//
//		VoucherBulkUploadEntity voucherBulkUploadEntity = new VoucherBulkUploadEntity();
//		ErupiVoucherBulkUploadSFListResponse bulkupload = new ErupiVoucherBulkUploadSFListResponse();
//		String response = "";
//		try {
//
//			response = MessageConstant.RESPONSE_FAILED;
//			Long orgId = request.getOrgId();
//			Long voucherId=request.getVoucherId();
//			String filename = request.getFileName();
//			String extn = CommonUtility.getFileExtension(filename);
//			String fileNameWithout = filename.substring(0, filename.lastIndexOf("."));
//			String uniqueFileName = CommonUtility.generateUniqueFileName(fileNameWithout, request.getOrgId(), extn);
//			LocalDate eventDate = LocalDate.now();
//			CopyUtility.copyProperties(request, voucherBulkUploadEntity);
//			voucherBulkUploadEntity.setCreationDate(eventDate);
//			voucherBulkUploadEntity.setFile(request.getFile());
//			voucherBulkUploadEntity.setFileName(uniqueFileName);
//			voucherBulkUploadEntity.setOrgId(orgId);
//			
//			//voucherBulkUploadEntity.setRedemtionType(re);
//			voucherBulkUploadEntity = erupiVoucherBulkDao.saveDetails(voucherBulkUploadEntity);
//
//			byte[] decodedBytes = request.getFile();
//			// Convert byte[] to InputStream
//			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
//			// Read Excel file using Apache POI
//			XSSFWorkbook workbook = null;
//			try {
//				workbook = new XSSFWorkbook(byteArrayInputStream);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
//			Iterator<Row> rowIterator = sheet.iterator();
//			boolean isHeaderRow = true;
//			int totalCount = 0;
//			int successCount = 0;
//			int failCount = 0;
//			while (rowIterator.hasNext()) {
//				Row row = rowIterator.next();
//				if (isHeaderRow) {
//					// Process the header row
//					String benName = row.getCell(1).getStringCellValue();
//					String mobile = row.getCell(2).getStringCellValue();
//					String amount = row.getCell(3).getStringCellValue();
//					String starDate = row.getCell(4).getStringCellValue();
//					String expDate = row.getCell(5).getStringCellValue();
//					totalCount = 0;
//					isHeaderRow = false;
//				} else {
//					// Process data row
//					totalCount++;
//					String voucherType = row.getCell(1).getStringCellValue();
//					String benName = row.getCell(2).getStringCellValue();
//					String mobile = "";
//					if (row.getCell(3).getCellType() == CellType.STRING) {
//						mobile = row.getCell(3).getStringCellValue();
//					} else {
//						mobile = String.valueOf((long) row.getCell(3).getNumericCellValue());
//					}
//					String amount = "";
//					if (row.getCell(4).getCellType() == CellType.STRING) {
//						amount = row.getCell(4).getStringCellValue();
//					} else {
//						amount = String.valueOf(row.getCell(4).getNumericCellValue());
//					}
//					Date starDate = row.getCell(5).getDateCellValue();
//					LocalDate stDate = CommonUtility.convertToLocalDate(starDate);
//					Date endDate = row.getCell(6).getDateCellValue();
//					LocalDate etDate = CommonUtility.convertToLocalDate(endDate);
//					//System.out.println(etDate);
//
//					boolean mob = CommonUtility.isValid(mobile);
//					boolean name = CommonUtility.isValidName(benName);
//					
//					boolean amountValid = CommonUtility.isValidAmount(amount,applicationConstantConfig.voucherCreationMinAmount,applicationConstantConfig.voucherCreationMaxAmount);
//					boolean expDateValid = CommonUtility.checkDate(etDate.toString());
//					String message = "";
//					message = mob == false ? "Invalid Mobile " : "";
//					// message=message==""?""
//					message += name == false ? "Invalid name" : "";
//					message += expDateValid == false ? "Invalid Date" : "";
//					
//					if (mob && name && amountValid && expDateValid) {		
//						
//						saveSuccess(request,voucherBulkUploadEntity.getId(),uniqueFileName,voucherType,
//								benName,mobile,amount,stDate,etDate,orgId,voucherId,"createdby");
//						successCount++;
//
//					} else {
//						VoucherBulkUploadFailEntity voucherBulkUploadFailEntity = new VoucherBulkUploadFailEntity();
//						
//						CopyUtility.copyProperties(request, voucherBulkUploadFailEntity);
//						voucherBulkUploadFailEntity.setBulktblId(voucherBulkUploadEntity.getId());
//						voucherBulkUploadFailEntity.setFileName(uniqueFileName);
//						voucherBulkUploadFailEntity.setVoucherType(voucherType);
//						voucherBulkUploadFailEntity.setRedemtionType(voucherType);
//						voucherBulkUploadFailEntity.setBeneficiaryName(benName);
//						voucherBulkUploadFailEntity.setMobile(mobile);
//						voucherBulkUploadFailEntity.setAmount(amount);
//						voucherBulkUploadFailEntity.setStartDate(stDate);
//						voucherBulkUploadFailEntity.setExpDate(etDate);
//						LocalDate curDate = LocalDate.now();
//						voucherBulkUploadFailEntity.setCreationDate(curDate);
//						voucherBulkUploadFailEntity.setMessage(message);
//						voucherBulkUploadFailEntity.setOrgId(orgId);
//						erupiVoucherBulkDao.saveFailDetails(voucherBulkUploadFailEntity);
//						failCount++;
//					}
//				}
//
//				
//			}
//
//			voucherBulkUploadEntity.setTotalCount(String.valueOf(totalCount));
//			voucherBulkUploadEntity.setSuccessCount(String.valueOf(successCount));
//			voucherBulkUploadEntity.setFailCount(String.valueOf(failCount));
//			erupiVoucherBulkDao.saveDetails(voucherBulkUploadEntity);
//
//			List<VoucherBulkUploadSuccessEntity> voucherSuccessList = erupiVoucherBulkDao.findSuccessList(orgId,
//					uniqueFileName);
//			List<VoucherBulkUploadFailEntity> voucherFailList = erupiVoucherBulkDao.findFailList(orgId, uniqueFileName);
//			bulkupload.setTotalCount(String.valueOf(totalCount));
//			bulkupload.setSuccessCount(String.valueOf(successCount));
//			bulkupload.setFailCount(String.valueOf(failCount));
//			bulkupload.setSuccess(voucherSuccessList);
//			bulkupload.setFail(voucherFailList);
//		} catch (Exception e) {
//			e.printStackTrace();
//			response = MessageConstant.RESPONSE_FAILED;
//			logger.error("Error :: " + e.getMessage());
//
//		}
//		return bulkupload;
//	}

	@Override
	public List<ErupiVoucherCreateDetailsRequest> createErupiVoucherBulkFile(
			ErupiVoucherBulkVoucherCreateRequest request) {
		VoucherBulkUploadSuccessEntity voEntity=new VoucherBulkUploadSuccessEntity();
		
		List<ErupiVoucherCreateDetailsRequest> erupiList=new ArrayList<ErupiVoucherCreateDetailsRequest>();
		try {
			List<String> idList = Arrays.asList(request.getArrayofid());
	        
			for (String id : idList) {
				ErupiVoucherCreateDetailsRequest erRequest=new ErupiVoucherCreateDetailsRequest();	
				Long idValue=Long.valueOf(id);
			
			voEntity=erupiVoucherBulkDao.findSuccessDetails(idValue);
			if(voEntity!=null) {
				CopyUtility.copyProperties(voEntity, erRequest);
				//CopyUtility.copyProperties(request, erRequest);
				MccMasterEntity vtme=new MccMasterEntity();
				vtme.setId(voEntity.getVoucherId());
				erRequest.setName(voEntity.getBeneficiaryName());
				erRequest.setAmount(voEntity.getAmount());
				//erRequest.setRedemtionType(request.getRedemtionType());
				erRequest.setBulktblId(idValue);
				erRequest.setVoucherId(vtme);
				erRequest.setMerchantId(request.getMerchantId());
				erRequest.setSubMerchantId(request.getSubMerchantId());
				erRequest.setAccountNumber(request.getAccountNumber());
				erRequest.setPayerVA(request.getPayerVA());
				erRequest.setPayerVA(request.getPayerVA());
				erRequest.setMandateType(request.getMandateType());
				erRequest.setPayeeVPA(request.getPayeeVPA());
				erRequest.setBankcode(request.getBankcode());
				//ErupiVoucherInitiateDetailsServiceImpl help=new ErupiVoucherInitiateDetailsServiceImpl();
				//String merTranId=help.getMerTranId(voEntity.getBankcode());
				erRequest=erupiVoucherInitiateDetailsService.saveErupiVoucherInitiateDetailsNewBulk(erRequest);
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
			String voucherType,String benName,String mobile,String amount,LocalDate stDate,LocalDate etDate,Long orgId,Long voucherId,String createdBy) {
		VoucherBulkUploadSuccessEntity voucherBulkUploadSuccessEntity = new VoucherBulkUploadSuccessEntity();
		CopyUtility.copyProperties(request, voucherBulkUploadSuccessEntity);
		voucherBulkUploadSuccessEntity.setBulktblId(id);
		voucherBulkUploadSuccessEntity.setPurposeCode(request.getVoucherCode());
		voucherBulkUploadSuccessEntity.setFileName(uniqueFileName);
		voucherBulkUploadSuccessEntity.setVoucherType(voucherType);
		voucherBulkUploadSuccessEntity.setRedemtionType(voucherType);
		voucherBulkUploadSuccessEntity.setBeneficiaryName(benName);
		voucherBulkUploadSuccessEntity.setMobile(mobile);
		voucherBulkUploadSuccessEntity.setAmount(amount);
		voucherBulkUploadSuccessEntity.setStartDate(stDate);		
		voucherBulkUploadSuccessEntity.setExpDate(etDate);
		voucherBulkUploadSuccessEntity.setStatus(1l);
		LocalDate curDate = LocalDate.now();
		voucherBulkUploadSuccessEntity.setCreationDate(curDate);
		voucherBulkUploadSuccessEntity.setCreatedby(createdBy);
		String benid=mobile+curDate.getDayOfMonth();
		voucherBulkUploadSuccessEntity.setBeneficiaryID(benid);
		voucherBulkUploadSuccessEntity.setOrgId(orgId);
		voucherBulkUploadSuccessEntity.setVoucherId(voucherId);
		erupiVoucherBulkDao.saveSuccessDetails(voucherBulkUploadSuccessEntity);
	}

//	@Override
//	public ErupiVoucherMasterUploadSFResponse saveErupiVoucherMasterFile(
//			ErupiVoucherMasterUploadRequest request) {
//		VoucherMasterUploadEntity voucherMasterUploadEntity = new VoucherMasterUploadEntity();
//		ErupiVoucherMasterUploadSFResponse bulkupload = new ErupiVoucherMasterUploadSFResponse();
//		String response = "";
//		try {
//
//			response = MessageConstant.RESPONSE_FAILED;
//			//Long orgId = request.getOrgId();
//			Long voucherId=request.getVoucherId();
//			String filename = request.getFileName();
//			String extn = CommonUtility.getFileExtension(filename);
//			String fileNameWithout = filename.substring(0, filename.lastIndexOf("."));
//			String uniqueFileName = CommonUtility.generateUniqueFileNameWithoutOrg(fileNameWithout, extn);
//			LocalDate eventDate = LocalDate.now();
//			CopyUtility.copyProperties(request, voucherMasterUploadEntity);
//			voucherMasterUploadEntity.setCreationDate(eventDate);
//			voucherMasterUploadEntity.setFile(request.getFile());
//			voucherMasterUploadEntity.setFileName(uniqueFileName);
//			//voucherBulkUploadEntity.setRedemtionType(re);
//			voucherMasterUploadEntity = erupiVoucherBulkDao.saveDetails(voucherMasterUploadEntity);
//
//			byte[] decodedBytes = request.getFile();
//			// Convert byte[] to InputStream
////			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
////			// Read Excel file using Apache POI
////			XSSFWorkbook workbook = null;
////			try {
////				workbook = new XSSFWorkbook(byteArrayInputStream);
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
//			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
//
//	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(byteArrayInputStream))) {
//	        	String header = reader.readLine(); // Discard header
//	            String line;
//	            while ((line = reader.readLine()) != null) {
//	                String[] values = line.split(","); // Split by commas for CSV format
//	                String  purposeCode=values[0];
//	                String  purposeDesc=values[1];
//	                String  mcc=values[2];
//	                String  mccDescription=values[3];	                  
//	               
//	                System.out.println();
//	            }
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//
////			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
////			Iterator<Row> rowIterator = sheet.iterator();
////			boolean isHeaderRow = true;
////			int totalCount = 0;
////			int successCount = 0;
////			int failCount = 0;
////			while (rowIterator.hasNext()) {
////				Row row = rowIterator.next();
////				if (isHeaderRow) {
////					// Process the header row
////					String benName = row.getCell(1).getStringCellValue();
////					String mobile = row.getCell(2).getStringCellValue();
////					String amount = row.getCell(3).getStringCellValue();
////					String starDate = row.getCell(4).getStringCellValue();
////					String expDate = row.getCell(5).getStringCellValue();
////					totalCount = 0;
////					isHeaderRow = false;
////				} else {
////					// Process data row
////					totalCount++;
////					String voucherType = row.getCell(1).getStringCellValue();
////					String benName = row.getCell(2).getStringCellValue();
////					String mobile = "";
////					if (row.getCell(3).getCellType() == CellType.STRING) {
////						mobile = row.getCell(3).getStringCellValue();
////					} else {
////						mobile = String.valueOf((long) row.getCell(3).getNumericCellValue());
////					}
////					String amount = "";
////					if (row.getCell(4).getCellType() == CellType.STRING) {
////						amount = row.getCell(4).getStringCellValue();
////					} else {
////						amount = String.valueOf(row.getCell(4).getNumericCellValue());
////					}
////					Date starDate = row.getCell(5).getDateCellValue();
////					LocalDate stDate = CommonUtility.convertToLocalDate(starDate);
////					Date endDate = row.getCell(6).getDateCellValue();
////					LocalDate etDate = CommonUtility.convertToLocalDate(endDate);
////					//System.out.println(etDate);
////
////					
////				}
//
//				
//			//}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			response = MessageConstant.RESPONSE_FAILED;
//			logger.error("Error :: " + e.getMessage());
//
//		}
//		return bulkupload;
//	}

	@Override
	public ErupiVoucherBulkUploadSFListResponse saveErupiVoucherBulkFileNew(
			ErupiVoucherBulkUploadRequest request) {
		VoucherBulkUploadEntity voucherBulkUploadEntity = new VoucherBulkUploadEntity();
		ErupiVoucherBulkUploadSFListResponse bulkupload = new ErupiVoucherBulkUploadSFListResponse();
		String response = "";
		try {

			response = MessageConstant.RESPONSE_FAILED;
			bulkupload.setResponse(response);
			request.setType("CREATE");
			Long orgId = request.getOrgId();
			Long voucherId=request.getVoucherId();
			String filename = request.getFileName();
			String extn = CommonUtility.getFileExtension(filename);
			String base64Encoded = Base64.getEncoder().encodeToString(request.getFile());
			  String dataString = request.getOrgId()+request.getFileName()+base64Encoded+request.getMcc()+request.getVoucherCode()+
					  request.getVoucherDesc()+request.getCreatedby()+request.getClientKey()+MessageConstant.SECRET_KEY;
			  System.out.println(dataString);
				String hash=ValidateConstants.generateHash(dataString);
				if(!request.getHash().equalsIgnoreCase(hash)) {
					bulkupload.setResponse(MessageConstant.HASH_ERROR);
					return bulkupload;
				}
			if(extn.equalsIgnoreCase(".xlsx")) {
				response=MessageConstant.FILE_ERROR;
				bulkupload.setResponse(response);
				return bulkupload;
			}
			String fileNameWithout = filename.substring(0, filename.lastIndexOf("."));
			String uniqueFileName = CommonUtility.generateUniqueFileName(fileNameWithout, request.getOrgId(), extn);
			LocalDate eventDate = LocalDate.now();
			CopyUtility.copyProperties(request, voucherBulkUploadEntity);
			voucherBulkUploadEntity.setPurposeCode(request.getVoucherCode());
			voucherBulkUploadEntity.setCreationDate(eventDate);
			voucherBulkUploadEntity.setFile(request.getFile());
			voucherBulkUploadEntity.setFileName(uniqueFileName);
			voucherBulkUploadEntity.setOrgId(orgId);
			String createdBy=request.getCreatedby();
			voucherBulkUploadEntity.setCreatedby(createdBy);
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
				 if (isRowEmpty(row)) {
				        break; // Exit the while loop if the row is blank
				    }
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
					String voucherType = "";
					if (row.getCell(1) != null) {
					    voucherType = row.getCell(1).getStringCellValue();
					}
					//String voucherType = row.getCell(1).getStringCellValue();
					//String benName = row.getCell(2).getStringCellValue();
					String benName = "";
					if (row.getCell(2) != null) {
					    benName = row.getCell(2).getStringCellValue();
					}
//					String mobile = "";
//					if (row.getCell(3).getCellType() == CellType.STRING) {
//						mobile = row.getCell(3).getStringCellValue();
//					} else {
//						mobile = String.valueOf((long) row.getCell(3).getNumericCellValue());
//					}
					String mobile = "";
					if (row.getCell(3) != null) {
					    if (row.getCell(3).getCellType() == CellType.STRING) {
					        mobile = row.getCell(3).getStringCellValue();
					    } else if (row.getCell(3).getCellType() == CellType.NUMERIC) {
					        mobile = String.valueOf((long) row.getCell(3).getNumericCellValue());
					    }
					}
					String amount = "";
					if (row.getCell(4) != null) {
					    if (row.getCell(4).getCellType() == CellType.STRING) {
					        amount = row.getCell(4).getStringCellValue();
					    } else if (row.getCell(4).getCellType() == CellType.NUMERIC) {
					        amount = String.valueOf(row.getCell(4).getNumericCellValue());
					    }
					}
//					String amount = "";
//					if (row.getCell(4).getCellType() == CellType.STRING) {
//						amount = row.getCell(4).getStringCellValue();
//					} else {
//						amount = String.valueOf(row.getCell(4).getNumericCellValue());
//					}
					//Date starDate = row.getCell(5).getDateCellValue();
					//Date startDate = null;
//					if (row.getCell(5) != null && row.getCell(5).getCellType() == CellType.DATE) {
//					    startDate = row.getCell(5).getDateCellValue();
//					}
					
					Date startDate = null;
					if (row.getCell(5) != null && row.getCell(5).getCellType() == CellType.NUMERIC) {
					    // Check if the cell contains a date
					    if (DateUtil.isCellDateFormatted(row.getCell(5))) {
					        startDate = row.getCell(5).getDateCellValue(); // Get the date value
					    }
					}
					LocalDate stDate = null;
					if (startDate != null) {
					    stDate = CommonUtility.convertToLocalDate(startDate);
					}

					String validity = "";
					if (row.getCell(6) != null) {
					    validity = row.getCell(6).getStringCellValue();
					}
					//LocalDate stDate = CommonUtility.convertToLocalDate(startDate);
					//String validity=
					//String validity = row.getCell(6).getStringCellValue();
					String[] daysArray=validity.split(" ");
					Long days=Long.valueOf(daysArray[0]);
					LocalDate etDate = null;
					if (stDate != null) {
					    etDate = stDate.plusDays(days);  // Add the days to the start date
					}
					//LocalDate etDate =stDate.plusDays(days); //CommonUtility.convertToLocalDate(endDate);
					System.out.println(etDate);

					boolean mob = CommonUtility.isValid(mobile);
					boolean name = CommonUtility.isValidName(benName);
					
					boolean amountValid = CommonUtility.isValidAmount(amount,applicationConstantConfig.voucherCreationMinAmount,applicationConstantConfig.voucherCreationMaxAmount);
					boolean expDateValid =false;
					boolean startDateValid =false;
					try {
						expDateValid = CommonUtility.checkDate(etDate.toString());
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						startDateValid = CommonUtility.checkStartDate(stDate.toString());
					} catch (Exception e) {
						// TODO: handle exception
					}
					 
					
					String message = "";
					message = mob == false ? "Invalid Mobile ," : "";
					//message=message==""?""
					message += name == false ? " Invalid name ," : "";
					message += startDateValid == false ? " Invalid Start Date ," : "";
					message += expDateValid == false ? " Invalid exp Date ," : "";
					message += amountValid == false ? " Amount should be between "+applicationConstantConfig.voucherCreationMinAmount+" and "+applicationConstantConfig.voucherCreationMaxAmount+".,"  : "";
					
					boolean empexit =false;
					if (mob) {
						String username ="";
						UserRequest userRequest = new UserRequest();
						userRequest.setMobile(mobile);
						TokenGeneration token = new TokenGeneration();
						String tokenvalue = token
								.getToken(applicationConstantConfig.authTokenApiUrl + CommonUtils.getToken);
						String response1 = CommonUtility.userRequest(tokenvalue,
								MessageConstant.gson.toJson(userRequest),
								applicationConstantConfig.userServiceApiUrl + CommonUtils.userDetailsWithMobile,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
						if (!ObjectUtils.isEmpty(response1)) {
							JSONObject demoRes = new JSONObject(response1);
							boolean status = demoRes.getBoolean("status");
							if (status) {
								//String username ="";
								if (demoRes.has("userEntity")) {
									JSONObject userEntity = demoRes.getJSONObject("userEntity");
									if (userEntity != null && userEntity.has("username")) {
									username = userEntity.optString("username", null);
									empexit=true;
									}
								}
							}
						}
						//check
						message += empexit == false ? "User does not exist" : "";
					}
					if (mob && name && amountValid && startDateValid && expDateValid && empexit) {		
						
						saveSuccess(request,voucherBulkUploadEntity.getId(),uniqueFileName,voucherType,
								benName,mobile,amount,stDate,etDate,orgId,voucherId,createdBy);
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
						voucherBulkUploadFailEntity.setCreatedby(createdBy);
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
			bulkupload.setResponse(MessageConstant.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response = MessageConstant.RESPONSE_FAILED;
			bulkupload.setResponse(response);
			logger.error("Error :: " + e.getMessage());

		}
		return bulkupload;
	}
	
	private boolean isRowEmpty(Row row) {
	    for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
	        Cell cell = row.getCell(i);
	        if (cell != null && !cell.toString().trim().isEmpty()) {
	            return false; // Row is not empty if at least one cell has content
	        }
	    }
	    return true; // Row is empty
	}

	@Override
	public ErupiVoucherBulkVoucherCreateRequest createErupiVoucherBulkFileHash(
			ErupiVoucherBulkVoucherCreateRequest request) {
		// TODO Auto-generated method stub
		try {
			request.setResponse(MessageConstant.RESPONSE_FAILED);
			
			String dataString = request.getOrgId()+request.getPurposeCode()+request.getMcc()+request.getPayerVA()+request.getMandateType()+
					request.getType()+request.getBankcode()+ request.getAccountNumber()+request.getVoucherCode()+request.getVoucherDesc()+
					request.getMerchantId()+request.getSubMerchantId()+request.getCreatedby()+request.getClientKey()+MessageConstant.SECRET_KEY;
			System.out.println(dataString);
			String hash=ValidateConstants.generateHash(dataString);
			if(!request.getHash().equalsIgnoreCase(hash)) {
				request.setResponse(MessageConstant.HASH_ERROR);
				return request;
			}
			request.setResponse(MessageConstant.RESPONSE_SUCCESS);
		} catch (Exception e) {
			request.setResponse(MessageConstant.RESPONSE_FAILED);
		}
		
		return request;
	}

}
