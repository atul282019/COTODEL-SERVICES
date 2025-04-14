package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingDao;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingListRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingNewRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.UpdateEmployeeStatusRequest;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployeeOnboardingService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.ValidateConstants;
@Repository
public class EmployeeOnboardingServiceImpl implements EmployeeOnboardingService{

	private static final Logger logger = LoggerFactory.getLogger(EmployeeOnboardingServiceImpl.class);

	
	@Autowired
	EmployeeOnboardingDao  employeeOnboardingDao;
	
	@Autowired
	ApplicationConstantConfig  applicationConstantConfig;
	
	@Autowired
    private EntityManager entityManager;
	
	@Override
	public EmployeeOnboardingRequest saveEmployeeDetails(EmployeeOnboardingRequest request) {
		
		String response="";
		String response1="";
		String tokenvalue="";
		
		TokenGeneration token=new TokenGeneration();
		UserRequest userRequest=new UserRequest();
		
		logger.info("file name::"+request.getFilename());
		logger.info("file type::"+request.getFiletype());
		
		String message=validateEmployeeOnboardingRequest(request);
		if(message!=null && !message.equalsIgnoreCase("")) {
			request.setResponse(message);
			return request;
		}
		
		try {
			String idval=request.getId()==null?"":request.getId().toString();
			String employerId=request.getEmployerId()==null?"":request.getEmployerId().toString();
			String employeeId=request.getEmployeeId()==null?"":request.getEmployeeId().toString();
			String name=request.getName();
			String email=request.getEmail();
			String mobile=request.getMobile();
			String herDate=request.getHerDate()==null?"":request.getHerDate().toString();
			String jobTitle=request.getJobTitle();
			String depratment=request.getDepratment();
			String ctc=request.getCtc();
			String location=request.getLocation();
			String residentOfIndia=request.getResidentOfIndia();
			String empOrCont=request.getEmpOrCont();
			String empPhoto=request.getEmpPhoto()==null?"":request.getEmpPhoto().toString();
			String base64Encoded ="";
			if(request.getEmpPhoto()!=null) {
			  base64Encoded = Base64.getEncoder().encodeToString(request.getEmpPhoto());
			}else {
				base64Encoded ="";
			}
			String filetype=request.getFiletype();
			String filename=request.getFilename();
			String clientKey=request.getClientKey();
			String managerId=request.getManagerId()==null?"":request.getManagerId().toString();
			String managerName=request.getManagerName()==null?"":request.getManagerName().toString();
			String dataString =idval+employerId+employeeId+name+email+mobile+herDate+jobTitle+depratment+ctc+
					location+residentOfIndia+empOrCont+base64Encoded+filetype+filename+managerId+managerName+clientKey+MessageConstant.SECRET_KEY;
			logger.info("dataString::"+dataString);
			String hash=ValidateConstants.generateHash(dataString);
			logger.info("hash::"+hash);
			if(!request.getHash().equalsIgnoreCase(hash)) {
				request.setResponse(MessageConstant.HASH_ERROR);
				return request;
			}
			tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
			userRequest.setUsername(request.getName());
			userRequest.setMobile(request.getMobile());
			userRequest.setEmail(request.getEmail());
			userRequest.setEmployerid(request.getEmployerId()==null?0:request.getEmployerId().intValue());
			response1 = CommonUtility.userRequest(tokenvalue, MessageConstant.gson.toJson(userRequest),
					applicationConstantConfig.userServiceApiUrl+CommonUtils.saveUsersWithOutMail,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
			if (!ObjectUtils.isEmpty(response1)) {
				JSONObject demoRes = new JSONObject(response1);
				boolean status = demoRes.getBoolean("status");
				if (status) {
					Long id=0l;
					if (demoRes.has("userEntity")) {
						JSONObject userEntity = demoRes.getJSONObject("userEntity");
						id=userEntity.getLong("id");
						
					}
					//String user = demoRes.getString("userEntity");
					//JSONObject refData=pendJosnIdRes.getJSONArray("data").getJSONObject(0);
					response = MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
					EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
					CopyUtility.copyProperties(request, employeeOnboarding);
					employeeOnboarding.setUserDetailsId(id);
					employeeOnboarding.setMode(1l);
					employeeOnboarding.setStatus(1);
					//byte[] pht=request.getEmpPhoto()!=null?request.getEmpPhoto().getBytes():null;
					//employeeOnboarding.setEmpPhoto(pht);
					//employeeOnboarding.setEmpCode();
					String empcode=request.getEmployerId()+"";
					String empCode=getEmpCode(request.getEmployerId());
					employeeOnboarding.setEmpCode(empCode);
					employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					response = MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);
				} else if (!status) {
					
					response = demoRes.getString("message");
					if(response.contains("User Already exist with this email or mobile number !!")) {
						if(request.getId()!=null) {
							response = MessageConstant.RESPONSE_FAILED;
							request.setResponse(response);
							EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
							employeeOnboarding=employeeOnboardingDao.getEmployeeOnboardingId(request.getId());
												 
							if(employeeOnboarding!=null) {
								employeeOnboarding.setEmpOrCont(request.getEmpOrCont());
								employeeOnboarding.setName(request.getName());
								employeeOnboarding.setEmail(request.getEmail());
								employeeOnboarding.setHerDate(request.getHerDate());
								employeeOnboarding.setJobTitle(request.getJobTitle());
								employeeOnboarding.setDepratment(request.getDepratment());
								employeeOnboarding.setManagerId(request.getManagerId());
								employeeOnboarding.setCtc(request.getCtc());
								employeeOnboarding.setLocation(request.getLocation());
								employeeOnboarding.setEmpPhoto(request.getEmpPhoto());
								employeeOnboarding.setResidentOfIndia(request.getResidentOfIndia());
								employeeOnboarding.setProofOfIdentity(request.getProofOfIdentity());
								employeeOnboarding.setBeneficiaryName(request.getBeneficiaryName());
								employeeOnboarding.setManagerName(request.getManagerName());
							employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
							response = MessageConstant.RESPONSE_SUCCESS;
							request.setResponse(response);
							}
							
						}else {
							request.setResponse(response);
						}
					}else {
						request.setResponse(response);
					}
					
				}

			}
		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}


	@Override
	public List<EmployeeOnboardingEntity> getEmployeeDetailsList(Long employerid) {
		List<EmployeeOnboardingEntity> employeeOnboading=null;
		try {
			employeeOnboading=employeeOnboardingDao.getEmployeeOnboardingList(employerid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return employeeOnboading;
	}


	@Override
	public EmployeeOnboardingRequest saveBulkEmployeeDetails(EmployeeOnboardingRequest request) {
		String response="";
		String response1="";
		String tokenvalue="";
		TokenGeneration token=new TokenGeneration();
		UserRequest userRequest=new UserRequest();
		try {
			tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
			String name=request.getName()==null?"":request.getName();
			userRequest.setUsername(name);
			userRequest.setMobile(request.getMobile());
			userRequest.setEmail(request.getEmail());
			userRequest.setEmployerid(request.getEmployerId()==null?0:request.getEmployerId().intValue());
			userRequest.setUpdateStatus(request.isUpdateStatus());
			userRequest.setUpdateStatus(request.isEmailStatus());
			
			response1 = CommonUtility.userRequest(tokenvalue, MessageConstant.gson.toJson(userRequest),
					applicationConstantConfig.userServiceApiUrl+CommonUtils.saveUsersBulk,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
			if (!ObjectUtils.isEmpty(response1)) {
				JSONObject demoRes = new JSONObject(response1);
				boolean status = demoRes.getBoolean("status");
				if (status) {
					Long id=0l;
					if (demoRes.has("userEntity")) {
						JSONObject userEntity = demoRes.getJSONObject("userEntity");
						id=userEntity.getLong("id");
						
					}
					response = MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
					EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
					CopyUtility.copyProperties(request, employeeOnboarding);
					employeeOnboarding.setName(request.getName());
					employeeOnboarding.setEmployerId(request.getEmployerId());
					employeeOnboarding.setUserDetailsId(id);
					employeeOnboarding.setMode(2l);
					employeeOnboarding.setStatus(1);
					String empCode=getEmpCode(request.getEmployerId());
					employeeOnboarding.setEmpCode(empCode);
					employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					response = MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);
				} else if (!status) {
					response = demoRes.getString("message");
					request.setResponse(response);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}


	@Override
	public EmployeeOnboardingListRequest confirmBulkEmployeeDetails(EmployeeOnboardingListRequest request) {
		String response="";
		String response1="";
		String tokenvalue="";
		
		TokenGeneration token=new TokenGeneration();
		UserRequest userRequest=new UserRequest();
		try {
			List<EmployeeOnboardingRequest> employeeConfirmOnboardingResponse=request.getEmployeeOnboardingRequest();
			tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
			for (EmployeeOnboardingRequest employeeOnboardingRequest : employeeConfirmOnboardingResponse) {
				
			
			userRequest.setUsername(employeeOnboardingRequest.getName());
			userRequest.setMobile(employeeOnboardingRequest.getMobile());
			userRequest.setEmail(employeeOnboardingRequest.getEmail());
			userRequest.setEmployerid(employeeOnboardingRequest.getEmployerId()==null?0:employeeOnboardingRequest.getEmployerId().intValue());
			response1 = CommonUtility.userRequest(tokenvalue, MessageConstant.gson.toJson(userRequest),
					applicationConstantConfig.userServiceApiUrl+CommonUtils.updateUser,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
			if (!ObjectUtils.isEmpty(response1)) {
				JSONObject demoRes = new JSONObject(response1);
				boolean status = demoRes.getBoolean("status");
				if (status) {
					Long id=0l;
					if (demoRes.has("userEntity")) {
						JSONObject userEntity = demoRes.getJSONObject("userEntity");
						id=userEntity.getLong("id");
						
					}
					//String user = demoRes.getString("userEntity");
					//JSONObject refData=pendJosnIdRes.getJSONArray("data").getJSONObject(0);
					
					response = MessageConstant.RESPONSE_FAILED;
					employeeOnboardingRequest.setResponse(response);
					EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
					employeeOnboarding=employeeOnboardingDao.getEmployeeOnboarding(employeeOnboardingRequest.getMobile());
					//CopyUtility.copyProperties(request, employeeOnboarding);
					//employeeOnboarding.setUserDetailsId(id);
					//employeeOnboarding.setMode(1l);
					employeeOnboarding.setStatus(1);
					employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					response = MessageConstant.RESPONSE_SUCCESS;
					employeeOnboardingRequest.setResponse(response);
				} else if (!status) {
					response = demoRes.getString("message");
					employeeOnboardingRequest.setResponse(response);
				}

			}
			}
			
		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			//employeeOnboardingRequest.setResponse(response);
		}
		
		return request;

	}


//	@Override
//	public EmployeeOnboardingListRequest tryBulkEmployeeDetails(EmployeeOnboardingListRequest request) {
//		
//		String response="";
//		String response1="";
//		String tokenvalue="";
//		TokenGeneration token=new TokenGeneration();
//		UserRequest userRequest=new UserRequest();
//		EmployeeOnboardingListRequest employeeOnboardingListRequest=new EmployeeOnboardingListRequest();
//		List<EmployeeOnboardingRequest> empList=new ArrayList<EmployeeOnboardingRequest>();
//		List<EmployeeOnboardingRequest> emList=new ArrayList<EmployeeOnboardingRequest>();
//		empList=request.getEmployeeOnboardingRequest();
//		for (EmployeeOnboardingRequest employeeOnboardingRequest : empList) {
//		try {
//			tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
//			
//			userRequest.setUsername(employeeOnboardingRequest.getName());
//			userRequest.setMobile(employeeOnboardingRequest.getMobile());
//			userRequest.setEmail(employeeOnboardingRequest.getEmail());
//			userRequest.setEmployerid(employeeOnboardingRequest.getEmployerId()==null?0:employeeOnboardingRequest.getEmployerId().intValue());
//			response1 = CommonUtility.userRequest(tokenvalue, MessageConstant.gson.toJson(userRequest),
//					applicationConstantConfig.userServiceApiUrl+CommonUtils.saveUsersWithOutMail,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
//			if (!ObjectUtils.isEmpty(response1)) {
//				JSONObject demoRes = new JSONObject(response1);
//				boolean status = demoRes.getBoolean("status");
//				if (status) {
//					Long id=0l;
//					if (demoRes.has("userEntity")) {
//						JSONObject userEntity = demoRes.getJSONObject("userEntity");
//						id=userEntity.getLong("id");
//						
//					}
//					response = MessageConstant.RESPONSE_FAILED;
//					employeeOnboardingRequest.setResponse(response);
//					EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
//					CopyUtility.copyProperties(request, employeeOnboarding);
//					employeeOnboarding.setUserDetailsId(id);
//					employeeOnboarding.setMode(1l);
//					
//					employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
//					response = MessageConstant.RESPONSE_SUCCESS;
//					employeeOnboardingRequest.setResponse(response);
//				} else if (!status) {
//					response = demoRes.getString("message");
//					employeeOnboardingRequest.setResponse(response);
//				}
//
//			}
//		
//		} catch (Exception e) {
//			response = MessageConstant.RESPONSE_FAILED;
//			employeeOnboardingRequest.setResponse(response);
//		}
//		emList.add(employeeOnboardingRequest);
//		}
//		employeeOnboardingListRequest.setEmployeeOnboardingRequest(emList);
//		return employeeOnboardingListRequest;
//
//	}


	@Override
	public EmployeeOnboardingEntity getEmployeeDetailsById(Long id) {
		EmployeeOnboardingEntity employeeOnboading=new EmployeeOnboardingEntity();
		try {
			employeeOnboading=employeeOnboardingDao.getEmployeeOnboardingId(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return employeeOnboading;
		
	}
	

	@Override
	public EmployeeOnboardingEntity getEmployeeDetailsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return employeeOnboardingDao.getEmployeeOnboardingUserId(userId);
	}


	@Override
	public EmployeeOnboardingNewRequest saveEmployeeDetailsNew(EmployeeOnboardingNewRequest request) {
		
		String response="";
		String response1="";
		String tokenvalue="";
		TokenGeneration token=new TokenGeneration();
		UserRequest userRequest=new UserRequest();
		try {
//			String dataString = request.getOrgId()+request.getBankName()+request.getAccountHolderName()+request.getAcNumber()+request.getConirmAccNumber()+request.getAccountType()+request.getIfsc()
//	        +request.getMobile()+request.getMerchentIid()+request.getSubmurchentid()+request.getPayerva()+request.getClientKey()+MessageConstant.SECRET_KEY;
			String dataString =request.getId()+request.getName()+request.getEmail()+
					request.getMobile()+request.getProofOfIdentity()+request.getClientKey()+MessageConstant.SECRET_KEY;
			String hash=ValidateConstants.generateHash(dataString);
			if(!request.getHash().equalsIgnoreCase(hash)) {
				request.setResponse(MessageConstant.HASH_ERROR);
				return request;
			}
			
			
			tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
			userRequest.setUsername(request.getName());
			userRequest.setMobile(request.getMobile());
			userRequest.setEmail(request.getEmail());
			userRequest.setEmployerid(request.getEmployerId()==null?0:request.getEmployerId().intValue());
			response1 = CommonUtility.userRequest(tokenvalue, MessageConstant.gson.toJson(userRequest),
					applicationConstantConfig.userServiceApiUrl+CommonUtils.saveUsersWithOutMailNew,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
			if (!ObjectUtils.isEmpty(response1)) {
				JSONObject demoRes = new JSONObject(response1);
				boolean status = demoRes.getBoolean("status");
				if (status) {
					Long id=0l;
					if (demoRes.has("userEntity")) {
						JSONObject userEntity = demoRes.getJSONObject("userEntity");
						id=userEntity.getLong("id");
						
					}
					response = MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
					EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
					employeeOnboarding=employeeOnboardingDao.getEmployeeOnboardingId(request.getId());
					if(employeeOnboarding!=null) {
						if(request.getProofOfIdentity()!=null) {
							employeeOnboarding.setProofOfIdentity(request.getProofOfIdentity());
						}
						if(request.getPan()!=null) {
							employeeOnboarding.setPan(request.getPan());
						}
						if(request.getBankAccountNumber()!=null) {
							employeeOnboarding.setBankAccountNumber(request.getBankAccountNumber());
						}
						if(request.getIfscCode()!=null) {
							employeeOnboarding.setIfscCode(request.getIfscCode());
						}
						if(request.getBeneficiaryName()!=null) {
							employeeOnboarding.setBeneficiaryName(request.getBeneficiaryName());
						}
//						employeeOnboarding.setEmpOrCont(request.getEmpOrCont());
//						employeeOnboarding.setName(request.getName());
//						employeeOnboarding.setEmail(request.getEmail());
//						employeeOnboarding.setHerDate(request.getHerDate());
//						employeeOnboarding.setJobTitle(request.getJobTitle());
//						employeeOnboarding.setDepratment(request.getDepratment());
//						employeeOnboarding.setManagerName(request.getManagerName());
//						employeeOnboarding.setCtc(request.getCtc());
//						employeeOnboarding.setLocation(request.getLocation());
//						employeeOnboarding.setResidentOfIndia(request.getResidentOfIndia());						
						employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					}else {
						employeeOnboarding = new EmployeeOnboardingEntity();
						CopyUtility.copyProperties(request, employeeOnboarding);
						employeeOnboarding.setCreationDate(LocalDateTime.now());
						employeeOnboarding.setUserDetailsId(id);
						employeeOnboarding.setMode(1l);
						employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					}
					
					response = MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);
					request.setId(employeeOnboarding.getId());
				} else if (!status) {
					response = demoRes.getString("message");
					request.setResponse(response);
				}

			}
		}catch (DataIntegrityViolationException e) {
			response=MessageConstant.DUP_ACC;
			request.setResponse(response);
			logger.error("Error in saveBankMaster :DataIntegrityViolationException:"+e.getMessage());
		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}
	
	public long generateId() {
        Query query = entityManager.createNativeQuery("SELECT nextval('empcode')");
        return ((Number) query.getSingleResult()).longValue();
    }
    
    public String getEmpCode(Long orgid) {
    	orgid=orgid==null?0:orgid;
    	String value=getEmpNo();
    	
        String uniqueId=orgid+value;
        System.out.println(uniqueId);
    	return uniqueId;
    }
    public String getEmpNo() {
    	
    	String value=String.valueOf(generateId());
    	int len =value.length();
    	String finalValue="";
        switch (len) {
            case 1:
            	finalValue="0000"+value;
                break;
            case 2:
            	finalValue="000"+value;
                break;
            case 3:
            	finalValue="00"+value;
                break;
            case 4:
            	finalValue="0"+value;
                break;
            default:
            	finalValue=value;
        }
    	
        System.out.println(finalValue);
    	return finalValue;
    }


	@Override
	public List<EmployeeOnboardingEntity> getEmployeeDetailsByManagerId(Long managerId) {
		List<EmployeeOnboardingEntity> employeeOnboading=new ArrayList<EmployeeOnboardingEntity>();
		try {
			employeeOnboading=employeeOnboardingDao.getEmployeeOnboardingManagerId(managerId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return employeeOnboading;
	}


	@Override
	public UpdateEmployeeStatusRequest updateEmployeeStatus(UpdateEmployeeStatusRequest request) {
		String response="";
		String response1="";
		String tokenvalue="";
		TokenGeneration token=new TokenGeneration();
		UserRequest userRequest=new UserRequest();
		try {
//			tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
//			userRequest.setUsername(request.getName());
//			userRequest.setMobile(request.getMobile());
//			userRequest.setEmail(request.getEmail());
//			userRequest.setEmployerid(request.getEmployerId()==null?0:request.getEmployerId().intValue());
//			response1 = CommonUtility.userRequest(tokenvalue, MessageConstant.gson.toJson(userRequest),
//					applicationConstantConfig.userServiceApiUrl+CommonUtils.saveUsersWithOutMail,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
//			if (!ObjectUtils.isEmpty(response1)) {
//				JSONObject demoRes = new JSONObject(response1);
//				boolean status = demoRes.getBoolean("status");
//				if (status) {
//					Long id=0l;
//					if (demoRes.has("userEntity")) {
//						JSONObject userEntity = demoRes.getJSONObject("userEntity");
//						id=userEntity.getLong("id");
//						
//					}
					response = MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
					EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
					employeeOnboarding=employeeOnboardingDao.getEmployeeOnboardingId(request.getId());
					if(request.getStatus().equalsIgnoreCase("Deactive")) {
						employeeOnboarding.setStatus(0);
					}else if(request.getStatus().equalsIgnoreCase("Active")) {
						employeeOnboarding.setStatus(1);
					}
					employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					response = MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);

		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;
	}


	private String validateEmployeeOnboardingRequest(EmployeeOnboardingRequest request) {
		String message="";
		if(request.getEmpOrCont().equalsIgnoreCase("Office Boy") || request.getEmpOrCont().equalsIgnoreCase("Driver") || request.getEmpOrCont().equalsIgnoreCase("Contractor")) {
	        message=ValidateConstants.validateMandatoryName(request.getName());
	        if(message!=null)
	        	return message;
		    message=ValidateConstants.validateMobile(request.getMobile());
		    if(message!=null)
		        return message;
		    if(request.getEmail()!=null) {
		       message=ValidateConstants.validateEmail(request.getEmail());
		       if(message!=null)
		       	return message;
		    }
		}else if(request.getEmpOrCont().equalsIgnoreCase("Employee")){
			 message=ValidateConstants.validateMandatoryName(request.getName());
		        if(message!=null)
		        	return message;
			 message=ValidateConstants.validateMobile(request.getMobile());
			    if(message!=null)
			        return message;
			 if(request.getEmail()!=null) {
			       message=ValidateConstants.validateEmail(request.getEmail());
			       if(message!=null)
			       	return message;
			    }
			 message=ValidateConstants.validateJoiningDate(request.getHerDate());
			    if(message!=null)
			        return message;
			    message=ValidateConstants.validateMandatoryName(request.getDepratment());
		        if(message!=null)
		        	return message;
		        
		        message=ValidateConstants.validateName(request.getManagerName());
		        if(message!=null)
		        	return message;
		        
		        message=ValidateConstants.validateCTC(request.getCtc());
		        if(message!=null)
		        	return message;
		        
		        
		}
		
//		 message=ValidateConstants.validateOrganizationId(directorOnboardingRequest.getOrgId());
//	        if(message!=null)
//	        	return message;
//	        message=ValidateConstants.validateMandatoryName(directorOnboardingRequest.getName());
//	        if(message!=null)
//	        	return message;
//	       message=ValidateConstants.validateEmail(directorOnboardingRequest.getEmail());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateMobile(directorOnboardingRequest.getMobile());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateDin(directorOnboardingRequest.getDin());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateDesignation(directorOnboardingRequest.getDesignation());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateAddress(directorOnboardingRequest.getAddress());
//	       if(message!=null)
//	       	return message;
//	       message=ValidateConstants.validateCreatedBy(directorOnboardingRequest.getCreatedby());
//	       
//	        return message;
		return "";
	}


	@Override
	public EmployeeOnboardingNewRequest updateEmployeeDetailsNew(EmployeeOnboardingNewRequest request) {
		String response="";
		String response1="";
		String tokenvalue="";
		TokenGeneration token=new TokenGeneration();
		UserRequest userRequest=new UserRequest();
		try {
			String dataString =request.getId()+request.getPan()+request.getIfscCode()+request.getBeneficiaryName()+
					request.getBankAccountNumber()+request.getClientKey()+MessageConstant.SECRET_KEY;
			
			String hash=ValidateConstants.generateHash(dataString);
			if(!request.getHash().equalsIgnoreCase(hash)) {
				request.setResponse(MessageConstant.HASH_ERROR);
				return request;
			}
			
			tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
			userRequest.setUsername(request.getName());
			userRequest.setMobile(request.getMobile());
			userRequest.setEmail(request.getEmail());
			userRequest.setEmployerid(request.getEmployerId()==null?0:request.getEmployerId().intValue());
			response1 = CommonUtility.userRequest(tokenvalue, MessageConstant.gson.toJson(userRequest),
					applicationConstantConfig.userServiceApiUrl+CommonUtils.saveUsersWithOutMailNew,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
			if (!ObjectUtils.isEmpty(response1)) {
				JSONObject demoRes = new JSONObject(response1);
				boolean status = demoRes.getBoolean("status");
				if (status) {
					Long id=0l;
					if (demoRes.has("userEntity")) {
						JSONObject userEntity = demoRes.getJSONObject("userEntity");
						id=userEntity.getLong("id");
						
					}
					response = MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
					EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
					employeeOnboarding=employeeOnboardingDao.getEmployeeOnboardingId(request.getId());
					if(employeeOnboarding!=null) {
						if(request.getProofOfIdentity()!=null) {
							employeeOnboarding.setProofOfIdentity(request.getProofOfIdentity());
						}
						if(request.getPan()!=null) {
							employeeOnboarding.setPan(request.getPan());
						}
						if(request.getBankAccountNumber()!=null) {
							employeeOnboarding.setBankAccountNumber(request.getBankAccountNumber());
						}
						if(request.getIfscCode()!=null) {
							employeeOnboarding.setIfscCode(request.getIfscCode());
						}
						if(request.getBeneficiaryName()!=null) {
							employeeOnboarding.setBeneficiaryName(request.getBeneficiaryName());
						}
					
						employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					}else {
						employeeOnboarding = new EmployeeOnboardingEntity();
						CopyUtility.copyProperties(request, employeeOnboarding);
						employeeOnboarding.setCreationDate(LocalDateTime.now());
						employeeOnboarding.setUserDetailsId(id);
						employeeOnboarding.setMode(1l);
						employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					}
					
					response = MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);
					request.setId(employeeOnboarding.getId());
				} else if (!status) {
					response = demoRes.getString("message");
					request.setResponse(response);
				}

			}
		}catch (DataIntegrityViolationException e) {
			response=MessageConstant.DUP_ACC;
			request.setResponse(response);
			logger.error("Error in saveBankMaster :DataIntegrityViolationException:"+e.getMessage());
		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}
	
	
}
