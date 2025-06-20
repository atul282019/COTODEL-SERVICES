package com.cotodel.hrms.auth.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
import com.cotodel.hrms.auth.server.dto.vehicle.RCData;
import com.cotodel.hrms.auth.server.dto.vehicle.RCRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleBulkCreateRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementBulkCreateRequest;
import com.cotodel.hrms.auth.server.dto.vehicle.VehicleManagementBulkUploadRequest;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehiclerBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.repository.vehicle.VehicleSequenceRepository;
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
	
	@Autowired
	VehicleSequenceRepository  vehicleSequenceRepository;
	
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
			//String dataString = request.getOrgId()+request.getFileName()+request.getFile()+request.getCreatedBy()+CLIENT_KEY+SECRET_KEY;
			if(!extn.equalsIgnoreCase("xlsx")) {
				response=MessageConstant.FILE_ERROR;
				bulkupload.setResponse(response);
				return bulkupload;
			}else if(request.getOrgId()==null) {
				response=MessageConstant.ORGNULL;
				bulkupload.setResponse(response);
				return bulkupload;
			}else if(request.getFile()==null) {
				response=MessageConstant.FILE_ERROR;
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
					int count = 0;
	                for (Cell cell : row) {
	                    if (!isCellEmpty(cell)) {
	                        count++;
	                    }
	                }

	                System.out.println("Non-blank column count in header: " + count);
//	                isHeaderRow = false;
//	                continue;
					//int count=row.getLastCellNum()+1;
					if(count!=2) {
						response=MessageConstant.FILE_ERROR;
						bulkupload.setResponse(response);
						return bulkupload;
					}
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
					vehicleNumber=vehicleNumber==null?"":vehicleNumber.toUpperCase();
					if (!vehicleNumber.equalsIgnoreCase("")) {
						String username ="";
						VehicleManagementEntity vehicle=vehicleManagementDao.getVehicleManagementByVehicleNo(vehicleNumber);
						if(vehicle!=null) {
							message=MessageConstant.VEHICLUNIQUE;
							saveFail(request,vehicleBulkUploadEntity.getId(),uniqueFileName,message,vehicleNumber);
							failCount++;
						}else {
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
									String msg = demoRes.getString("message");
									String jsonString = msg.toString();
									message=jsonString;
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
		vehiclerBulkUploadFailEntity.setVehicleNumber(vehicleNumber);
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
	private static boolean isCellEmpty(Cell cell) {
        if (cell == null) return true;

        CellType cellType = cell.getCellType();
        if (cellType == CellType.STRING) {
            return cell.getStringCellValue().trim().isEmpty();
        } else if (cellType == CellType.BLANK) {
            return true;
        } else if (cellType == CellType.FORMULA) {
            return cell.getCellFormula().trim().isEmpty();
        } else if (cellType == CellType.NUMERIC || cellType == CellType.BOOLEAN) {
            return false;
        } else {
            return true;
        }
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
public List<VehicleManagementBulkCreateRequest> createErupiVoucherBulkFile(
		VehicleBulkCreateRequest request) {
	VehicleBulkUploadSuccessEntity voEntity=new VehicleBulkUploadSuccessEntity();
	
	List<VehicleManagementBulkCreateRequest> vehicle=new ArrayList<VehicleManagementBulkCreateRequest>();
	try {
		List<String> idList = Arrays.asList(request.getArrayofid());
        
		for (String id : idList) {
			VehicleManagementBulkCreateRequest erRequest=new VehicleManagementBulkCreateRequest();	
			Long idValue=Long.valueOf(id);
		
		voEntity=vehicleBulkUploadDao.findSuccessDetails(idValue);
		if(voEntity!=null) {
			VehicleManagementEntity vehicleManagementEntity=new VehicleManagementEntity();
			CopyUtility.copyProperties(voEntity, vehicleManagementEntity);
			vehicleManagementEntity.setId(null);
			vehicleManagementEntity.setCreationType("Bulk");
			vehicleManagementEntity.setApplicationType("Web");
			vehicleManagementEntity.setStatus(1);
			vehicleManagementEntity.setCreationDate(LocalDateTime.now());
			vehicleManagementEntity.setCreatedBy(request.getCreatedby());
			vehicleManagementEntity.setOwnerName(voEntity.getOwner_name());
			vehicleManagementEntity.setOwnerAddress(voEntity.getPresent_address());
			vehicleManagementEntity.setFuelType(voEntity.getFuel_type());
			vehicleManagementEntity.setVehicleModel(voEntity.getMaker_model());
			int seat=voEntity.getSeat_capacity()==null?0:Integer.valueOf(voEntity.getSeat_capacity());
			vehicleManagementEntity.setSeat(seat);
			vehicleManagementEntity.setVehicleType(voEntity.getVehicle_category_description());
			vehicleManagementEntity.setVehicleManufactor(voEntity.getMaker_model());
			vehicleManagementEntity.setVehicleFuelType(voEntity.getFuel_type());
			vehicleManagementEntity.setRequestType("API Call");
			String sequenceId=vehicleSequenceId();
			vehicleManagementEntity.setVehicleSequenceId(sequenceId);
			//vehicleManagementEntity.setWeight(id);
			
			erRequest.setVehicleNumber(voEntity.getVehicleNumber());
			
			vehicleManagementDao.saveVehicleManagementDetails(vehicleManagementEntity);
			vehicleBulkUploadDao.updateSuccessFlag(idValue);
			erRequest.setResponse(MessageConstant.RESPONSE_SUCCESS);
		}
		vehicle.add(erRequest);
		}
	} catch (Exception e) {
		logger.error("Error :: " + e.getMessage());
	}
	
	return vehicle;
}
public String vehicleSequenceId() {
	
	String  sequence=String.valueOf(fetchNextSequenceValue());
	
	String sequenceValue="";
	String finalSequenceValue="";
	
	if(sequence.length()==1) {
		sequenceValue="00"+sequence;
	}else if(sequence.length()==2) {
		sequenceValue="0"+sequence;
	}else {
		sequenceValue=sequence;
	}
	
	finalSequenceValue=monthYear()+"-"+sequenceValue;
	
	return finalSequenceValue;
}
public String monthYear() {
	
	LocalDate date = LocalDate.now();		
	String month=String.valueOf(date.getMonthValue());
	String year=String.valueOf(date.getYear());
	String str="VM-";
	String  monthValue=month.length()==1?"0"+month:month;
	String  yearValue=year.substring(2,4);
	
	return str+monthValue+yearValue;
}
public Long fetchNextSequenceValue() {
    return vehicleSequenceRepository.getNextSeriesId();
 }

}
