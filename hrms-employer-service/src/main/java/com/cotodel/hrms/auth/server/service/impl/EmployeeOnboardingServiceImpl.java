package com.cotodel.hrms.auth.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@Repository
public class EmployeeOnboardingServiceImpl implements EmployeeOnboardingService{

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
		try {
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


	@Override
	public EmployeeOnboardingListRequest tryBulkEmployeeDetails(EmployeeOnboardingListRequest request) {
		
		String response="";
		String response1="";
		String tokenvalue="";
		TokenGeneration token=new TokenGeneration();
		UserRequest userRequest=new UserRequest();
		EmployeeOnboardingListRequest employeeOnboardingListRequest=new EmployeeOnboardingListRequest();
		List<EmployeeOnboardingRequest> empList=new ArrayList<EmployeeOnboardingRequest>();
		List<EmployeeOnboardingRequest> emList=new ArrayList<EmployeeOnboardingRequest>();
		empList=request.getEmployeeOnboardingRequest();
		for (EmployeeOnboardingRequest employeeOnboardingRequest : empList) {
		try {
			tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
			
			userRequest.setUsername(employeeOnboardingRequest.getName());
			userRequest.setMobile(employeeOnboardingRequest.getMobile());
			userRequest.setEmail(employeeOnboardingRequest.getEmail());
			userRequest.setEmployerid(employeeOnboardingRequest.getEmployerId()==null?0:employeeOnboardingRequest.getEmployerId().intValue());
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
					response = MessageConstant.RESPONSE_FAILED;
					employeeOnboardingRequest.setResponse(response);
					EmployeeOnboardingEntity employeeOnboarding = new EmployeeOnboardingEntity();
					CopyUtility.copyProperties(request, employeeOnboarding);
					employeeOnboarding.setUserDetailsId(id);
					employeeOnboarding.setMode(1l);
					
					employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					response = MessageConstant.RESPONSE_SUCCESS;
					employeeOnboardingRequest.setResponse(response);
				} else if (!status) {
					response = demoRes.getString("message");
					employeeOnboardingRequest.setResponse(response);
				}

			}
		
		} catch (Exception e) {
			response = MessageConstant.RESPONSE_FAILED;
			employeeOnboardingRequest.setResponse(response);
		}
		emList.add(employeeOnboardingRequest);
		}
		employeeOnboardingListRequest.setEmployeeOnboardingRequest(emList);
		return employeeOnboardingListRequest;

	}


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
	public EmployeeOnboardingNewRequest saveEmployeeDetailsNew(EmployeeOnboardingNewRequest request) {
		
		String response="";
		String response1="";
		String tokenvalue="";
		TokenGeneration token=new TokenGeneration();
		UserRequest userRequest=new UserRequest();
		try {
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
				if (status || !status) {
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
					employeeOnboarding=employeeOnboardingDao.getEmployeeOnboarding(request.getMobile());
					if(employeeOnboarding!=null) {
						employeeOnboarding.setProofOfIdentity(request.getProofOfIdentity());
						employeeOnboarding.setPan(request.getPan());
						employeeOnboarding.setBankAccountNumber(request.getBankAccountNumber());
						employeeOnboarding.setIfscCode(request.getIfscCode());
						employeeOnboarding.setBeneficiaryName(request.getBeneficiaryName());
					}else {
						employeeOnboarding = new EmployeeOnboardingEntity();
						CopyUtility.copyProperties(request, employeeOnboarding);
					}
					
					employeeOnboarding.setUserDetailsId(id);
					employeeOnboarding.setMode(1l);
					employeeOnboarding = employeeOnboardingDao.saveDetails(employeeOnboarding);
					response = MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);
					request.setId(employeeOnboarding.getId());
				} else if (!status) {
					response = demoRes.getString("message");
					request.setResponse(response);
				}

			}
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


	
	
	
}
