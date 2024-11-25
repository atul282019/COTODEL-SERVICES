package com.cotodel.hrms.auth.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherBulkDao;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.service.ErupiVoucherCreationBulkService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
@Service
public class ErupiVoucherCreationBulkServiceImpl implements ErupiVoucherCreationBulkService{
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherCreationBulkServiceImpl.class);
	
	@Autowired
	ErupiVoucherBulkDao erupiVoucherBulkDao;
	
	@Override
	public ErupiVoucherBulkUploadSFListResponse saveErupiVoucherBulkFile(
			ErupiVoucherBulkUploadRequest erupiVoucherBulkUploadRequest) {
		
		VoucherBulkUploadEntity voucherBulkUploadEntity=new VoucherBulkUploadEntity();
		ErupiVoucherBulkUploadSFListResponse bulkupload=new ErupiVoucherBulkUploadSFListResponse();
		try {
		Long orgId=erupiVoucherBulkUploadRequest.getOrgId();
		 String filename=erupiVoucherBulkUploadRequest.getFileName();
		 String extn=CommonUtility.getFileExtension(filename);
	     String fileNameWithout = filename.substring(0, filename.lastIndexOf("."));
	     String uniqueFileName=CommonUtility.generateUniqueFileName(fileNameWithout, erupiVoucherBulkUploadRequest.getOrgId(), extn);
		 LocalDate eventDate = LocalDate.now();	
		 voucherBulkUploadEntity.setCreationDate(eventDate);
		 voucherBulkUploadEntity.setFile(erupiVoucherBulkUploadRequest.getFile());
		 voucherBulkUploadEntity.setFileName(uniqueFileName);
		 voucherBulkUploadEntity.setOrgId(orgId);
		 voucherBulkUploadEntity=erupiVoucherBulkDao.saveDetails(voucherBulkUploadEntity);
		 
		 
		 
		 byte[] decodedBytes = erupiVoucherBulkUploadRequest.getFile();
	        // Convert byte[] to InputStream
	        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
	        
	        // Read Excel file using Apache POI
	        XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(byteArrayInputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        Sheet sheet = workbook.getSheetAt(0);  // Assuming data is in the first sheet
	        Iterator<Row> rowIterator = sheet.iterator();
	        boolean isHeaderRow = true;
	        int totalCount=0;
	        int successCount=0;
	        int failCount=0;
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	            if (isHeaderRow) {
                    // Process the header row
	 	            String benName = row.getCell(1).getStringCellValue();
	 	            String mobile = row.getCell(2).getStringCellValue();
	 	            String amount = row.getCell(3).getStringCellValue();
	 	            String starDate = row.getCell(4).getStringCellValue();
	 	            String expDate = row.getCell(5).getStringCellValue();
	 	           totalCount=0;
                    isHeaderRow = false;
                } else {
                    // Process data row
                	 String voucherType = row.getCell(1).getStringCellValue();
     	             String benName = row.getCell(2).getStringCellValue();
     	             String mobile="";
     	            if(row.getCell(3).getCellType()==CellType.STRING) {
     	            	mobile = row.getCell(3).getStringCellValue();
     	            }else {
     	            	mobile =String.valueOf((long)row.getCell(3).getNumericCellValue());
     	            }
     	           String amount ="";
     	          if(row.getCell(4).getCellType()==CellType.STRING) {
     	        	 amount = row.getCell(4).getStringCellValue();
   	            	}else {
   	            	amount =String.valueOf(row.getCell(4).getNumericCellValue());
   	            	}
     	         Date starDate =row.getCell(5).getDateCellValue();
     	         LocalDate stDate=CommonUtility.convertToLocalDate(starDate);
     	         Date endDate =row.getCell(6).getDateCellValue();
     	         LocalDate etDate=CommonUtility.convertToLocalDate(endDate);
     	         System.out.println(etDate);
     	         
     	         boolean mob=CommonUtility.isValid(mobile);
     	         boolean name=CommonUtility.isValidName(mobile);
     	        String message="";
     	        message=mob==false?"Invalid Mobile ":"";
     	       //message=message==""?""
     	        message+=name==false?"Invalid name":"";
     	         if(mob && name) {
     	        	VoucherBulkUploadSuccessEntity voucherBulkUploadSuccessEntity=new VoucherBulkUploadSuccessEntity();
     	        	voucherBulkUploadSuccessEntity.setFileName(uniqueFileName);
     	        	voucherBulkUploadSuccessEntity.setVoucherType(voucherType);
     	        	voucherBulkUploadSuccessEntity.setBeneficiaryName(benName);
     	        	voucherBulkUploadSuccessEntity.setMobile(mobile);
     	        	voucherBulkUploadSuccessEntity.setAmount(amount);
     	        	voucherBulkUploadSuccessEntity.setStartDate(stDate);
     	        	voucherBulkUploadSuccessEntity.setExpDate(etDate);
     	        	LocalDate curDate = LocalDate.now();	
     	        	voucherBulkUploadSuccessEntity.setCreationDate(curDate);
     	        	voucherBulkUploadSuccessEntity.setOrgId(orgId);
     	        	erupiVoucherBulkDao.saveSuccessDetails(voucherBulkUploadSuccessEntity);
     	        	 successCount++;
     		       
     	         }else {
     	        	VoucherBulkUploadFailEntity voucherBulkUploadFailEntity=new VoucherBulkUploadFailEntity();
     	        	voucherBulkUploadFailEntity.setFileName(uniqueFileName);
     	        	voucherBulkUploadFailEntity.setVoucherType(voucherType);
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
	            
	            totalCount++;
	            
	       
	    }
	        
	        voucherBulkUploadEntity.setTotalCount(String.valueOf(totalCount));
	        voucherBulkUploadEntity.setSuccessCount(String.valueOf(successCount));
	        voucherBulkUploadEntity.setFailCount(String.valueOf(failCount));
	        erupiVoucherBulkDao.saveDetails(voucherBulkUploadEntity);
	        
	        List<VoucherBulkUploadSuccessEntity> voucherSuccessList=erupiVoucherBulkDao.findSuccessList(orgId, uniqueFileName);
	        List<VoucherBulkUploadFailEntity> voucherFailList=erupiVoucherBulkDao.findFailList(orgId, uniqueFileName);
	       // bulkupload.setTotalCount(uniqueFileName);
	        bulkupload.setTotalCount(String.valueOf(totalCount));
	        bulkupload.setSuccessCount(String.valueOf(successCount));
	        bulkupload.setFailCount(String.valueOf(failCount));
	        bulkupload.setSuccess(voucherSuccessList);
	        bulkupload.setFail(voucherFailList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :: "+e.getMessage());
			
		}
		return bulkupload;
	}
	 

}
