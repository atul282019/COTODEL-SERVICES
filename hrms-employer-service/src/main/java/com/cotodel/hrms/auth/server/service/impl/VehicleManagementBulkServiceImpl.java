package com.cotodel.hrms.auth.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.auth.server.dao.VehicleBulkUploadDao;
import com.cotodel.hrms.auth.server.dao.VehicleManagementDao;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.RCData;
import com.cotodel.hrms.auth.server.dto.vehicle.RCRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleBulkCreateRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementBulkUploadRequest;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehiclerBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.VehicleManagementBulkService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.google.gson.Gson;
@Service
public class VehicleManagementBulkServiceImpl implements VehicleManagementBulkService{
	private static final Logger logger = LoggerFactory.getLogger(VehicleManagementBulkServiceImpl.class);
	
	@Autowired
	VehicleBulkUploadDao vehicleBulkUploadDao;
	
	@Autowired
	VehicleManagementDao vehicleManagementDao;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public VehicleBulkUploadSFListResponse saveVehicleBulkFileNew(
			VehicleManagementBulkUploadRequest request) {
		VehicleBulkUploadEntity vehicleBulkUploadEntity = new VehicleBulkUploadEntity();
		VehicleBulkUploadSFListResponse bulkupload = new VehicleBulkUploadSFListResponse();
		String response = "";
		try {

			response = MessageConstant.RESPONSE_FAILED;
			bulkupload.setResponse(response);
			Long orgId = request.getOrgId();
//			Long voucherId=request.getVoucherId();
			String filename = request.getFileName();
			String extn = CommonUtility.getFileExtension(filename);
			String base64Encoded = Base64.getEncoder().encodeToString(request.getFile());
			if(extn.equalsIgnoreCase(".xlsx")) {
				response=MessageConstant.FILE_ERROR;
				bulkupload.setResponse(response);
				return bulkupload;
			}else if(request.getOrgId()==null) {
				response=MessageConstant.ORGNULL;
				bulkupload.setResponse(response);
				return bulkupload;
			}
			String fileNameWithout = filename.substring(0, filename.lastIndexOf("."));
			String uniqueFileName = CommonUtility.generateUniqueFileName(fileNameWithout, request.getOrgId(), extn);
			CopyUtility.copyProperties(request, vehicleBulkUploadEntity);
			vehicleBulkUploadEntity.setCreationDate(LocalDateTime.now());
			vehicleBulkUploadEntity.setFile(request.getFile());
			vehicleBulkUploadEntity.setFileName(uniqueFileName);
			vehicleBulkUploadEntity.setOrgId(orgId);
			String createdBy=request.getCreatedBy();
			vehicleBulkUploadEntity.setCreatedBy(createdBy);
			vehicleBulkUploadEntity = vehicleBulkUploadDao.saveDetails(vehicleBulkUploadEntity);

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
					String vehno = row.getCell(1).getStringCellValue();
					totalCount = 0;
					isHeaderRow = false;
				} else {
					// Process data row
					totalCount++;
					String vehicleNumber = "";
					if (row.getCell(1) != null) {
						vehicleNumber = row.getCell(1).getStringCellValue();
					}

					RCData rcData=new RCData();
					boolean empexit =false;
					String message="";
					if (!vehicleNumber.equalsIgnoreCase("")) {
						String username ="";
						RCRequest rcRequest = new RCRequest();
						rcRequest.setId_number(vehicleNumber);
						rcRequest.setCreatedBy(createdBy);
						rcRequest.setOrgId(orgId);
						TokenGeneration token = new TokenGeneration();
						String tokenvalue = token
								.getToken(applicationConstantConfig.authTokenApiUrl + CommonUtils.getToken);
						String response1 = CommonUtility.userRequest(tokenvalue,
								MessageConstant.gson.toJson(rcRequest),
								applicationConstantConfig.gstServiceApiUrl + CommonUtils.getVehicleDetails,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
						if (!ObjectUtils.isEmpty(response1)) {
							JSONObject demoRes = new JSONObject(response1);
							boolean status = demoRes.getBoolean("status");
							if (status) {
								if (demoRes.has("data")) {
									empexit=true;
									JSONObject data = demoRes.getJSONObject("data");
									String jsonString = data.toString();
									rcData=jsonToPOJO(jsonString);

								}
							}else {
								if (demoRes.has("message")) {
									JSONObject msg = demoRes.getJSONObject("message");
									String jsonString = msg.toString();
									message=jsonString;
								}
							}
						}
					}
					if (empexit) {						
						saveSuccess(request,vehicleBulkUploadEntity.getId(),uniqueFileName,rcData,vehicleNumber);
						successCount++;
					} else {
						saveFail(request,vehicleBulkUploadEntity.getId(),uniqueFileName,message,vehicleNumber);
						failCount++;
					}
				}

				
			}

			vehicleBulkUploadEntity.setTotalCount(String.valueOf(totalCount));
			vehicleBulkUploadEntity.setSuccessCount(String.valueOf(successCount));
			vehicleBulkUploadEntity.setFailCount(String.valueOf(failCount));
			vehicleBulkUploadDao.saveDetails(vehicleBulkUploadEntity);

			List<VehicleBulkUploadSuccessEntity> voucherSuccessList = vehicleBulkUploadDao.findSuccessList(orgId,uniqueFileName);
			List<VehiclerBulkUploadFailEntity> voucherFailList = vehicleBulkUploadDao.findFailList(orgId, uniqueFileName);
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
	public void saveSuccess(VehicleManagementBulkUploadRequest request,Long id,String uniqueFileName,RCData rcData,String vehicleNumber) {
		VehicleBulkUploadSuccessEntity vehicleBulkUploadSuccessEntity = new VehicleBulkUploadSuccessEntity();
		CopyUtility.copyProperties(rcData, vehicleBulkUploadSuccessEntity);
		vehicleBulkUploadSuccessEntity.setBulktblId(id);
		vehicleBulkUploadSuccessEntity.setFileName(uniqueFileName);
		vehicleBulkUploadSuccessEntity.setStatus(1l);
		vehicleBulkUploadSuccessEntity.setCreationDate(LocalDateTime.now());
		vehicleBulkUploadSuccessEntity.setCreatedby(request.getCreatedBy());
		vehicleBulkUploadSuccessEntity.setVehicleNumber(vehicleNumber);
		vehicleBulkUploadSuccessEntity.setOrgId(request.getOrgId());
		vehicleBulkUploadDao.saveSuccessDetails(vehicleBulkUploadSuccessEntity);
	}
	public void saveFail(VehicleManagementBulkUploadRequest request,Long id,String uniqueFileName,String message,String vehicleNumber) {
		VehiclerBulkUploadFailEntity vehiclerBulkUploadFailEntity = new VehiclerBulkUploadFailEntity();
		
		CopyUtility.copyProperties(request, vehiclerBulkUploadFailEntity);						
		vehiclerBulkUploadFailEntity.setBulktblId(id);
		vehiclerBulkUploadFailEntity.setFileName(uniqueFileName);
		vehiclerBulkUploadFailEntity.setCreationDate(LocalDateTime.now());
		vehiclerBulkUploadFailEntity.setCreatedby(request.getCreatedBy());
		vehiclerBulkUploadFailEntity.setMessage(message);
		vehiclerBulkUploadFailEntity.setOrgId(request.getOrgId());
		vehicleBulkUploadDao.saveFailDetails(vehiclerBulkUploadFailEntity);
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


public  RCData jsonToPOJO(String data) {
		
		Gson gson = new Gson();
		RCData rcData =new RCData();
		try {
			rcData = gson.fromJson(data, RCData.class);
		} catch (Exception e) {
			logger.error("error in RCData..."+e.getMessage());
		}
		
        return rcData;
	}
@Override
public List<ErupiVoucherCreateDetailsRequest> createErupiVoucherBulkFile(
		VehicleBulkCreateRequest request) {
	VehicleBulkUploadSuccessEntity voEntity=new VehicleBulkUploadSuccessEntity();
	
	List<ErupiVoucherCreateDetailsRequest> erupiList=new ArrayList<ErupiVoucherCreateDetailsRequest>();
	try {
		List<String> idList = Arrays.asList(request.getArrayofid());
        
		for (String id : idList) {
			ErupiVoucherCreateDetailsRequest erRequest=new ErupiVoucherCreateDetailsRequest();	
			Long idValue=Long.valueOf(id);
		
		voEntity=vehicleBulkUploadDao.findSuccessDetails(idValue);
		if(voEntity!=null) {
			VehicleManagementEntity vehicleManagementEntity=new VehicleManagementEntity();
			CopyUtility.copyProperties(voEntity, vehicleManagementEntity);
			vehicleManagementEntity.setCreationType("Bulk");
			vehicleManagementEntity.setApplicationType("Web");
			
			vehicleManagementDao.saveVehicleManagementDetails(vehicleManagementEntity);
			vehicleBulkUploadDao.updateSuccessFlag(idValue);
			
		}
		erupiList.add(erRequest);
		}
	} catch (Exception e) {
		logger.error("Error :: " + e.getMessage());
	}
	
	return erupiList;
}


}
