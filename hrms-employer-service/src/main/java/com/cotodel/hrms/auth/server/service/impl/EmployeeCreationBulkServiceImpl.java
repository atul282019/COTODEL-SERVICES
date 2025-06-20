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

import javax.persistence.EntityManager;

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

import com.cotodel.hrms.auth.server.dao.EmployeeBulkDao;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkCreateRequest;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiBulkIdRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkVoucherCreateRequest;
import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployeeCreationBulkService;
import com.cotodel.hrms.auth.server.service.EmployeeOnboardingService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Service
public class EmployeeCreationBulkServiceImpl implements EmployeeCreationBulkService{
	private static final Logger logger = LoggerFactory.getLogger(EmployeeCreationBulkServiceImpl.class);
	
	@Autowired
	EmployeeBulkDao employeeBulkDao;
	
//	@Autowired
//	ErupiVoucherInitiateDetailsService erupiVoucherInitiateDetailsService;
//	
	@Autowired
    private EntityManager entityManager;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	EmployeeOnboardingService employeeOnboardingService;
	
	


	@Override
	public List<EmployeeOnboardingRequest> createErupiEmployeeBulkFile(EmployeeBulkCreateRequest request) {
		EmployeeBulkUploadSuccessEntity empEntity=new EmployeeBulkUploadSuccessEntity();
		
		List<EmployeeOnboardingRequest> erupiList=new ArrayList<EmployeeOnboardingRequest>();

		try {
			List<String> idList = Arrays.asList(request.getArrayofid());
	        
			for (String id : idList) {
				EmployeeOnboardingRequest erRequest=new EmployeeOnboardingRequest();	
				Long idValue=Long.valueOf(id);
			
				empEntity=employeeBulkDao.findSuccessDetails(idValue);
			if(empEntity!=null) {
				CopyUtility.copyProperties(empEntity, erRequest);
				
				erRequest.setName(empEntity.getBeneficiaryName());
				erRequest=employeeOnboardingService.saveBulkEmployeeDetails(erRequest);
			}
			employeeBulkDao.updateSuccessFlag(idValue);
			erupiList.add(erRequest);
			}
		} catch (Exception e) {
			logger.error("Error :: " + e.getMessage());
		}
		
		return erupiList;
	}
//	public long getMerchantTranId() {
//        Query query = entityManager.createNativeQuery("SELECT nextval('merchanttranid')");
//        return ((Number) query.getSingleResult()).longValue();
//    }
//    
//    public String getMerTranId(String bankcode) {
//    	bankcode=bankcode==null?"":bankcode;
//    	Long value=getMerchantTranId();
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
//        String date = sdf.format(new Date());
//        String uniqueId=bankcode+date+value;
//        System.out.println(uniqueId);
//    	return uniqueId;
//    }

	
	
//	@Override
//	public int updateEmployeeStatus(ErupiBulkIdRequest eBulkIdRequest) {
//		// TODO Auto-generated method stub
//		int result=0;
//		try {
//			 result=employeeBulkDao.updateSuccessFlag(eBulkIdRequest.getId());
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return result;
//	}
	
	public void saveSuccess(EmployeeBulkUploadRequest request,Long id,String uniqueFileName,
			String userType,String benName,String mobile,Long orgId,String createdBy) {
		EmployeeBulkUploadSuccessEntity employeeBulkUploadSuccessEntity = new EmployeeBulkUploadSuccessEntity();
		CopyUtility.copyProperties(request, employeeBulkUploadSuccessEntity);
		employeeBulkUploadSuccessEntity.setBulktblId(id);
		employeeBulkUploadSuccessEntity.setFileName(uniqueFileName);
		employeeBulkUploadSuccessEntity.setUserType(userType);
		employeeBulkUploadSuccessEntity.setBeneficiaryName(benName);
		employeeBulkUploadSuccessEntity.setMobile(mobile);
		employeeBulkUploadSuccessEntity.setStatus(1l);
		LocalDate curDate = LocalDate.now();
		employeeBulkUploadSuccessEntity.setCreationDate(LocalDateTime.now());
		employeeBulkUploadSuccessEntity.setCreatedby(createdBy);
		String benid=mobile+curDate.getDayOfMonth();
		employeeBulkUploadSuccessEntity.setOrgId(orgId);
		employeeBulkDao.saveSuccessDetails(employeeBulkUploadSuccessEntity);
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
	@Override
	public EmployeeBulkUploadSFListResponse saveEmployeeBulkFileUpload(
			EmployeeBulkUploadRequest request) {
		EmployeeBulkUploadEntity employeeBulkUploadEntity = new EmployeeBulkUploadEntity();
		EmployeeBulkUploadSFListResponse bulkupload = new EmployeeBulkUploadSFListResponse();
		String response = "";
		try {

			response = MessageConstant.RESPONSE_FAILED;
			bulkupload.setResponse(response);
			//request.setType("CREATE");
			Long orgId = request.getOrgId();
			//Long voucherId=request.getVoucherId();
			String filename = request.getFileName();
			String extn = CommonUtility.getFileExtension(filename);
			String base64Encoded = Base64.getEncoder().encodeToString(request.getFile());
//			  String dataString = request.getOrgId()+request.getFileName()+base64Encoded+request.getMcc()+request.getVoucherCode()+
//					  request.getVoucherDesc()+request.getCreatedby()+request.getClientKey()+MessageConstant.SECRET_KEY;
//			  System.out.println(dataString);
//				String hash=ValidateConstants.generateHash(dataString);
//				if(!request.getHash().equalsIgnoreCase(hash)) {
//					bulkupload.setResponse(MessageConstant.HASH_ERROR);
//					return bulkupload;
//				}
			if(extn.equalsIgnoreCase(".xlsx")) {
				response=MessageConstant.FILE_ERROR;
				bulkupload.setResponse(response);
				return bulkupload;
			}
			String fileNameWithout = filename.substring(0, filename.lastIndexOf("."));
			String uniqueFileName = CommonUtility.generateUniqueFileName(fileNameWithout, request.getOrgId(), extn);
			LocalDate eventDate = LocalDate.now();
			CopyUtility.copyProperties(request, employeeBulkUploadEntity);
			//voucherBulkUploadEntity.setPurposeCode(request.getVoucherCode());
			employeeBulkUploadEntity.setCreationDate(eventDate);
			employeeBulkUploadEntity.setFile(request.getFile());
			employeeBulkUploadEntity.setFileName(uniqueFileName);
			employeeBulkUploadEntity.setOrgId(orgId);
			String createdBy=request.getCreatedby();
			employeeBulkUploadEntity.setCreatedby(createdBy);
			//voucherBulkUploadEntity.setRedemtionType(re);
			employeeBulkUploadEntity = employeeBulkDao.saveDetails(employeeBulkUploadEntity);

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
					if(count!=4) {
						response=MessageConstant.FILE_ERROR;
						bulkupload.setResponse(response);
						return bulkupload;
					}
					String userType = row.getCell(1).getStringCellValue();
					String name = row.getCell(2).getStringCellValue();
					String mobile = row.getCell(3).getStringCellValue();
					totalCount = 0;
					isHeaderRow = false;
				} else {
					// Process data row

//					System.out.println(count);
					totalCount++;
					String userType = "";
					if (row.getCell(1) != null) {
						userType = row.getCell(1).getStringCellValue();
					}

					String benName = "";
					if (row.getCell(2) != null) {
						 if (row.getCell(2).getCellType() == CellType.STRING) {
							 benName = row.getCell(2).getStringCellValue();
						    } else if (row.getCell(2).getCellType() == CellType.NUMERIC) {
						    	benName = String.valueOf((long) row.getCell(2).getNumericCellValue());
						    }
					}

					String mobile = "";
					if (row.getCell(3) != null) {
					    if (row.getCell(3).getCellType() == CellType.STRING) {
					        mobile = row.getCell(3).getStringCellValue();
					    } else if (row.getCell(3).getCellType() == CellType.NUMERIC) {
					        mobile = String.valueOf((long) row.getCell(3).getNumericCellValue());
					    }
					}

					boolean mob = CommonUtility.isValid(mobile);
					boolean name = CommonUtility.isValidName(benName);		 
					
					String message = "";
					message = mob == false ? "Invalid Mobile ," : "";
					message += name == false ? " Invalid name ," : "";
					boolean empexit =false;
					if (mob) {
						String username ="";
						UserRequest userRequest = new UserRequest();
						userRequest.setMobile(mobile);
						userRequest.setEmployerid(orgId.intValue());
						TokenGeneration token = new TokenGeneration();
						String tokenvalue = token
								.getToken(applicationConstantConfig.authTokenApiUrl + CommonUtils.getToken);
						String response1 = CommonUtility.userRequest(tokenvalue,
								MessageConstant.gson.toJson(userRequest),
								applicationConstantConfig.userServiceApiUrl + CommonUtils.userWithMobile,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
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
					if (mob && name && empexit) {		
						
						saveSuccess(request,employeeBulkUploadEntity.getId(),uniqueFileName,userType,
								benName,mobile,orgId,createdBy);
						successCount++;

					} else {
						EmployeeBulkUploadFailEntity employeeBulkUploadFailEntity = new EmployeeBulkUploadFailEntity();
						
						CopyUtility.copyProperties(request, employeeBulkUploadFailEntity);
						employeeBulkUploadFailEntity.setBulktblId(employeeBulkUploadEntity.getId());
						employeeBulkUploadFailEntity.setFileName(uniqueFileName);
						employeeBulkUploadFailEntity.setUserType(userType);
						employeeBulkUploadFailEntity.setBeneficiaryName(benName);
						employeeBulkUploadFailEntity.setMobile(mobile);
						employeeBulkUploadFailEntity.setCreationDate(LocalDateTime.now());
						employeeBulkUploadFailEntity.setCreatedby(createdBy);
						employeeBulkUploadFailEntity.setMessage(message);
						employeeBulkUploadFailEntity.setOrgId(orgId);
						employeeBulkDao.saveFailDetails(employeeBulkUploadFailEntity);
						failCount++;
					}
				}

				
			}

			employeeBulkUploadEntity.setTotalCount(String.valueOf(totalCount));
			employeeBulkUploadEntity.setSuccessCount(String.valueOf(successCount));
			employeeBulkUploadEntity.setFailCount(String.valueOf(failCount));
			employeeBulkDao.saveDetails(employeeBulkUploadEntity);

			List<EmployeeBulkUploadSuccessEntity> voucherSuccessList = employeeBulkDao.findSuccessList(orgId,uniqueFileName);
			List<EmployeeBulkUploadFailEntity> voucherFailList = employeeBulkDao.findFailList(orgId, uniqueFileName);
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
	public EmployeeBulkCreateRequest createErupiVoucherBulkFileHash(EmployeeBulkCreateRequest request) {
		// TODO Auto-generated method stub
		try {
			request.setResponse(MessageConstant.RESPONSE_FAILED);
			
			String dataString = request.getOrgId()+request.getClientKey()+MessageConstant.SECRET_KEY;
			System.out.println(dataString);
//			String hash=ValidateConstants.generateHash(dataString);
//			if(!request.getHash().equalsIgnoreCase(hash)) {
//				request.setResponse(MessageConstant.HASH_ERROR);
//				return request;
//			}else if(request.getAccountNumber()==null || request.getAccountNumber().equalsIgnoreCase("")) {
//				request.setResponse(MessageConstant.ACCOUNTNULL);
//				return request;
//			}else if(request.getOrgId()==null || request.getOrgId()==0) {
//				request.setResponse(MessageConstant.ORGNULL);
//				return request;
//			}
			request.setResponse(MessageConstant.RESPONSE_SUCCESS);
		} catch (Exception e) {
			request.setResponse(MessageConstant.RESPONSE_FAILED);
		}
		
		return request;
	}




	

}
