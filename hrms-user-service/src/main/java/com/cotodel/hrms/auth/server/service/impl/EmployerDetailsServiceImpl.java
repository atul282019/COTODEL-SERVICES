/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.EmployerDetailsDao;
import com.cotodel.hrms.auth.server.dao.UserDetailsDao;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsRequest;
import com.cotodel.hrms.auth.server.dto.EmployerProfileAddress;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployerDetailsService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.ValidateConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * 
 */

@Service
public class EmployerDetailsServiceImpl implements EmployerDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(EmployerDetailsServiceImpl.class);
	
	@Autowired
	public EmployerDetailsDao employerDetailsDao;
	
	@Autowired
	public UserDetailsDao userDetailsDao;
	
	@Autowired
    private EntityManager entityManager;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;

	@Override
	public EmployerDetailsRequest saveEmployerDetails(EmployerDetailsRequest employerDetailsRequest) {
		// TODO Auto-generated method stub
		logger.info(" inside saveEmployerDetails");
		EmployerDetailsEntity employerDetailsEntity= new EmployerDetailsEntity();
		//EmployerDetailsEntity empDetailsEntity= new EmployerDetailsEntity();
		String response="";
		response=MessageConstant.RESPONSE_FAILED;
		employerDetailsRequest.setResponse(response);
		System.out.println("first method::");
		try {			
//			String errorMessage =SQLInjectionValidator.validateFieldsForSQLInjection(employerDetailsRequest);
//			System.out.println("errorMessage::"+errorMessage);
//			logger.info("errorMessage"+errorMessage);
//	        if (errorMessage != null) {
//	        	employerDetailsRequest.setResponse(errorMessage);
//				return employerDetailsRequest;
//	        }
//	        String dataString = employerDetailsRequest.getPan()+employerDetailsRequest.getPan()+employerDetailsRequest.getLegalNameOfBusiness()+employerDetailsRequest.getLegalNameOfBusiness()+employerDetailsRequest.getConstitutionOfBusiness()+employerDetailsRequest.getOrgType()+
//	        		employerDetailsRequest.getAddress1()+employerDetailsRequest.getAddress2()+employerDetailsRequest.getDistrictName()+employerDetailsRequest.getPincode()+
//	        		employerDetailsRequest.getStateName()+employerDetailsRequest.getCreatedBy()+employerDetailsRequest.getEmployerId()+employerDetailsRequest.getClientKey()+MessageConstant.SECRET_KEY;
			String dataString = employerDetailsRequest.getPan()+employerDetailsRequest.getLegalNameOfBusiness()+
					employerDetailsRequest.getTradeName()+employerDetailsRequest.getConstitutionOfBusiness()+
					employerDetailsRequest.getOrgType()+employerDetailsRequest.getAddress1()+
					employerDetailsRequest.getAddress2()+employerDetailsRequest.getDistrictName()+
					employerDetailsRequest.getPincode()+employerDetailsRequest.getStateName()+
					employerDetailsRequest.getGstIdentificationNumber()+employerDetailsRequest.getCreatedBy()+
					employerDetailsRequest.getEmployerId()+employerDetailsRequest.getClientKey()+MessageConstant.SECRET_KEY;
//	        String dataString =employerDetailsRequest.getLegalNameOfBusiness()+employerDetailsRequest.getTradeName()+employerDetailsRequest.getConstitutionOfBusiness()+
//	        		employerDetailsRequest.getOrgType()+employerDetailsRequest.getAddress1()+employerDetailsRequest.getAddress2()+
//	        		employerDetailsRequest.getDistrictName()+employerDetailsRequest.getPincode()+employerDetailsRequest.getStateName()+
//	        		employerDetailsRequest.getGstIdentificationNumber()+employerDetailsRequest.getPan()+employerDetailsRequest.getCreatedBy()+
//	        		employerDetailsRequest.getEmployerId()+employerDetailsRequest.getClientKey()+MessageConstant.SECRET_KEY;
	        System.out.println("first hash::"+employerDetailsRequest.getHash());
	        
	        String hash=ValidateConstants.generateHash(dataString);
	        System.out.println("first local hash::"+hash);
			if(!employerDetailsRequest.getHash().equalsIgnoreCase(hash)) {
				employerDetailsRequest.setResponse(MessageConstant.HASH_ERROR);
				return employerDetailsRequest;
			}
			employerDetailsEntity=employerDetailsDao.getEmployerDetails(employerDetailsRequest.getEmployerId());
			if(employerDetailsEntity!=null) {
				logger.info("if");
				//CopyUtility.copyProperties(employerDetailsRequest,empDetailsEntity);
				//empDetailsEntity.setId(employerDetailsEntity.getId());
				//empDetailsEntity.setEmployerId(employerDetailsEntity.getEmployerId());
				//employerDetailsEntity.setEmployerCode(employerDetailsEntity.getEmployerCode());
				//employerDetailsEntity.setCreatedBy(employerDetailsEntity.getCreatedBy());
				//empDetailsEntity.setCreatedDate(employerDetailsEntity.getCreatedDate());
				employerDetailsEntity.setUpdatedDate(LocalDate.now());
				employerDetailsEntity.setLegalNameOfBusiness(employerDetailsRequest.getLegalNameOfBusiness());
				employerDetailsEntity.setTradeName(employerDetailsRequest.getTradeName());
				employerDetailsEntity.setConstitutionOfBusiness(employerDetailsRequest.getConstitutionOfBusiness());
				employerDetailsEntity.setOrgType(employerDetailsRequest.getOrgType());
				employerDetailsEntity.setAddress1(employerDetailsRequest.getAddress1());
				employerDetailsEntity.setAddress2(employerDetailsRequest.getAddress2());
				employerDetailsEntity.setDistrictName(employerDetailsRequest.getDistrictName());
				employerDetailsEntity.setPincode(employerDetailsRequest.getPincode());
				employerDetailsEntity.setStateName(employerDetailsRequest.getStateName());
				employerDetailsEntity.setConsent(employerDetailsRequest.getConsent());
				employerDetailsEntity.setStreetName(employerDetailsRequest.getStreetName());
				employerDetailsEntity.setBuildingNumber(employerDetailsRequest.getBuildingNumber());
				employerDetailsEntity.setBuildingName(employerDetailsRequest.getBuildingName());
				employerDetailsEntity.setLocation(employerDetailsRequest.getLocation());
				employerDetailsEntity.setFloorNumber(employerDetailsRequest.getFloorNumber());
				employerDetailsEntity.setOtpStatus(employerDetailsRequest.getOtpStatus());
				employerDetailsEntity.setNatureOfPrincipalPlaceOfBusiness(employerDetailsRequest.getNatureOfPrincipalPlaceOfBusiness());
				employerDetailsEntity.setCenterJurisdictionCode(employerDetailsRequest.getCenterJurisdictionCode());
				employerDetailsEntity.setGstIdentificationNumber(employerDetailsRequest.getGstIdentificationNumber());
				employerDetailsEntity.setPan(employerDetailsRequest.getPan());
				employerDetailsEntity.setUpdatedBy(employerDetailsRequest.getCreatedBy());				 
				employerDetailsEntity.setStatus(1);
				if(employerDetailsRequest.getOtpStatus()!=null && employerDetailsRequest.getOtpStatus().equalsIgnoreCase("YES")) {
					employerDetailsEntity.setProfileComplete(3);
				}else {
					employerDetailsEntity.setProfileComplete(2);
				}
				//empDetailsEntity.setProfileComplete(3);
				employerDetailsEntity.setConsent(employerDetailsRequest.getConsent());
				employerDetailsEntity.setOtpStatus(employerDetailsRequest.getOtpStatus());
				employerDetailsEntity.setUpdatedBy(employerDetailsRequest.getCreatedBy());
				employerDetailsDao.saveCompanyDetails(employerDetailsEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
				employerDetailsRequest.setResponse(response);				
			}
//			else {
//				logger.info("else");
//				CopyUtility.copyProperties(employerDetailsRequest,empDetailsEntity);
//				empDetailsEntity.setCreatedDate(LocalDate.now());
//				//empDetailsEntity.setcre
//				empDetailsEntity.setStatus(1);
//				String employerCode=getEmployerNo();
//				empDetailsEntity.setProfileComplete(2);
//				empDetailsEntity.setEmployerCode(employerCode);
//				employerDetailsDao.saveCompanyDetails(empDetailsEntity);		
//				response=MessageConstant.RESPONSE_SUCCESS;
//				employerDetailsRequest.setResponse(response);
//			}
		} catch (Exception e) {
			// TODO: handle exception
			response=MessageConstant.RESPONSE_FAILED;
			employerDetailsRequest.setResponse(response);
		}
		return employerDetailsRequest;
	}
	
	public long generateId() {
        Query query = entityManager.createNativeQuery("SELECT nextval('employercode')");
        return ((Number) query.getSingleResult()).longValue();
    }
public String getEmployerNo() {
    	
    	String value=String.valueOf(generateId());
    	int len =value.length();
    	String finalValue="";
    	String employerCode="";
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
        employerCode="HRMS"+finalValue;
        System.out.println(employerCode);
    	return employerCode;
    }

@Override
public EmployerDetailsEntity getEmployerDetails(Long employerId) {
	// TODO Auto-generated method stub
	return employerDetailsDao.getEmployerDetails(employerId);
}

@Override
public EmployerDetailsEntity getEmployerComplete(Long employerId) {
	EmployerDetailsEntity employerDetailsEntity= new EmployerDetailsEntity();
	String response="";
	//int comp=0;
	response=MessageConstant.RESPONSE_FAILED;
	try {			
		employerDetailsEntity=employerDetailsDao.getEmployerDetails(employerId);
//		if(employerDetailsEntity!=null) {
//			comp=employerDetailsEntity.getProfileComplete();
//		}else {
//			UserEntity userEntity=userDetailsDao.getOrgExist(employerId);
//			if(userEntity!=null) {
//				int role=userEntity.getRole_id();
//				if(role==1 || role==9) {
//					employerDetailsEntity= new EmployerDetailsEntity();
//					employerDetailsEntity.setEmployerId(employerId);
//					employerDetailsEntity.setProfileComplete(1);
//				}
//			}
//		}
		
	}catch (Exception e) {
			e.printStackTrace();
		}
	return employerDetailsEntity;
}

@Override
public List<EmployerProfileAddress> getCompaneyAddress(Long employerId) {
	EmployerDetailsEntity employerDetailsEntity= new EmployerDetailsEntity();
	employerDetailsEntity=employerDetailsDao.getEmployerDetails(employerId);
	List<EmployerProfileAddress> list=new ArrayList<>();
	if(employerDetailsEntity!=null) {
		EmployerProfileAddress employerProfileAddress=new EmployerProfileAddress();
		String address=employerDetailsEntity.getAddress1()+"-"+employerDetailsEntity.getAddress2();
		employerProfileAddress.setId(employerDetailsEntity.getId());
		employerProfileAddress.setOfficeAddress(address);
		list.add(employerProfileAddress);
	}
	
	return list;
}

@Override
public EmployerDetailsRequest updateEmployerDetails(EmployerDetailsRequest employerDetailsRequest) {
	// TODO Auto-generated method stub
			logger.info(" inside saveEmployerDetails");
			EmployerDetailsEntity employerDetailsEntity= new EmployerDetailsEntity();
			EmployerDetailsEntity empDetailsEntity= new EmployerDetailsEntity();
			String response="";
			response=MessageConstant.RESPONSE_FAILED;
			employerDetailsRequest.setResponse(response);
			System.out.println("first method::");
			try {			
//				String errorMessage =SQLInjectionValidator.validateFieldsForSQLInjection(employerDetailsRequest);
//				System.out.println("errorMessage::"+errorMessage);
//				logger.info("errorMessage"+errorMessage);
//		        if (errorMessage != null) {
//		        	employerDetailsRequest.setResponse(errorMessage);
//					return employerDetailsRequest;
//		        }
//		        String dataString = employerDetailsRequest.getPan()+employerDetailsRequest.getPan()+employerDetailsRequest.getLegalNameOfBusiness()+employerDetailsRequest.getLegalNameOfBusiness()+employerDetailsRequest.getConstitutionOfBusiness()+employerDetailsRequest.getOrgType()+
//		        		employerDetailsRequest.getAddress1()+employerDetailsRequest.getAddress2()+employerDetailsRequest.getDistrictName()+employerDetailsRequest.getPincode()+
//		        		employerDetailsRequest.getStateName()+employerDetailsRequest.getCreatedBy()+employerDetailsRequest.getEmployerId()+employerDetailsRequest.getClientKey()+MessageConstant.SECRET_KEY;
//				
//		        String dataString =employerDetailsRequest.getLegalNameOfBusiness()+employerDetailsRequest.getTradeName()+employerDetailsRequest.getConstitutionOfBusiness()+
//		        		employerDetailsRequest.getOrgType()+employerDetailsRequest.getAddress1()+employerDetailsRequest.getAddress2()+
//		        		employerDetailsRequest.getDistrictName()+employerDetailsRequest.getPincode()+employerDetailsRequest.getStateName()+
//		        		employerDetailsRequest.getGstIdentificationNumber()+employerDetailsRequest.getPan()+employerDetailsRequest.getCreatedBy()+
//		        		employerDetailsRequest.getEmployerId()+employerDetailsRequest.getClientKey()+MessageConstant.SECRET_KEY;
				
//				String dataString =employerDetailsRequest.getLegalNameOfBusiness()+employerDetailsRequest.getTradeName()+
//						employerDetailsRequest.getConstitutionOfBusiness()+employerDetailsRequest.getOrgType()+
//						employerDetailsRequest.getAddress1()+employerDetailsRequest.getAddress2()+
//						employerDetailsRequest.getDistrictName()+employerDetailsRequest.getPincode()+
//						employerDetailsRequest.getStateName()+employerDetailsRequest.getGstIdentificationNumber()+
//						employerDetailsRequest.getPan()+employerDetailsRequest.getCreatedBy()+
//						employerDetailsRequest.getEmployerId()+employerDetailsRequest.getConsent()+
//						employerDetailsRequest.getOtpStatus()+employerDetailsRequest.getClientKey()+MessageConstant.SECRET_KEY;
				String dataString =employerDetailsRequest.getPan()+employerDetailsRequest.getLegalNameOfBusiness()+
						employerDetailsRequest.getTradeName()+employerDetailsRequest.getConstitutionOfBusiness()+
						employerDetailsRequest.getOrgType()+employerDetailsRequest.getAddress1()+
						employerDetailsRequest.getAddress2()+employerDetailsRequest.getDistrictName()+
						employerDetailsRequest.getPincode()+employerDetailsRequest.getStateName()+
						employerDetailsRequest.getGstIdentificationNumber()+employerDetailsRequest.getCreatedBy()+
						employerDetailsRequest.getEmployerId()+employerDetailsRequest.getConsent()+
						employerDetailsRequest.getOtpStatus()+employerDetailsRequest.getClientKey()+MessageConstant.SECRET_KEY;
		        System.out.println("first hash::"+employerDetailsRequest.getHash());
		        
		        String hash=ValidateConstants.generateHash(dataString);
		        System.out.println("first local hash::"+hash);
				if(!employerDetailsRequest.getHash().equalsIgnoreCase(hash)) {
					employerDetailsRequest.setResponse(MessageConstant.HASH_ERROR);
					return employerDetailsRequest;
				}
				employerDetailsEntity=employerDetailsDao.getEmployerDetails(employerDetailsRequest.getEmployerId());
				if(employerDetailsEntity!=null) {
					logger.info("if");
					//CopyUtility.copyProperties(employerDetailsRequest,empDetailsEntity);
					//empDetailsEntity.setId(employerDetailsEntity.getId());
					//empDetailsEntity.setEmployerId(employerDetailsEntity.getEmployerId());
					//empDetailsEntity.setEmployerCode(employerDetailsEntity.getEmployerCode());
					//empDetailsEntity.setCreatedBy(employerDetailsEntity.getCreatedBy());
					//empDetailsEntity.setCreatedDate(employerDetailsEntity.getCreatedDate());
					employerDetailsEntity.setUpdatedDate(LocalDate.now());
					employerDetailsEntity.setUpdatedBy(employerDetailsRequest.getCreatedBy());
					employerDetailsEntity.setStatus(1);
					if(employerDetailsRequest.getOtpStatus()!=null && employerDetailsRequest.getOtpStatus().equalsIgnoreCase("YES")) {
						employerDetailsEntity.setProfileComplete(3);
					}else {
						employerDetailsEntity.setProfileComplete(2);
					}
					//empDetailsEntity.setProfileComplete(3);
					employerDetailsEntity.setConsent(employerDetailsRequest.getConsent());
					employerDetailsEntity.setOtpStatus(employerDetailsRequest.getOtpStatus());
					employerDetailsEntity.setUpdatedBy(employerDetailsRequest.getCreatedBy());
					employerDetailsDao.saveCompanyDetails(employerDetailsEntity);
					response=MessageConstant.RESPONSE_SUCCESS;
					employerDetailsRequest.setResponse(response);				
				}
//				else {
//					logger.info("else");
//					CopyUtility.copyProperties(employerDetailsRequest,empDetailsEntity);
//					empDetailsEntity.setCreatedDate(LocalDate.now());
//					//empDetailsEntity.setcre
//					empDetailsEntity.setStatus(1);
//					String employerCode=getEmployerNo();
//					empDetailsEntity.setProfileComplete(2);
//					empDetailsEntity.setEmployerCode(employerCode);
//					employerDetailsDao.saveCompanyDetails(empDetailsEntity);		
//					response=MessageConstant.RESPONSE_SUCCESS;
//					employerDetailsRequest.setResponse(response);
//				}
			} catch (Exception e) {
				// TODO: handle exception
				response=MessageConstant.RESPONSE_FAILED;
				employerDetailsRequest.setResponse(response);
			}
			return employerDetailsRequest;
}

	@Override
	public EmployerDetailsRequest saveEmployerOnboardingDetails(EmployerDetailsRequest employerDetailsRequest) {
		// TODO Auto-generated method stub
					logger.info(" inside saveEmployerDetails");
					EmployerDetailsEntity employerDetailsEntity= new EmployerDetailsEntity();
					EmployerDetailsEntity empDetailsEntity= new EmployerDetailsEntity();
					String response="";
					response=MessageConstant.RESPONSE_FAILED;
					employerDetailsRequest.setResponse(response);
					System.out.println("first method::");
					try {			
				

//						String dataString =employerDetailsRequest.getPan()+employerDetailsRequest.getLegalNameOfBusiness()+
//								employerDetailsRequest.getTradeName()+employerDetailsRequest.getConstitutionOfBusiness()+
//								employerDetailsRequest.getOrgType()+employerDetailsRequest.getAddress1()+
//								employerDetailsRequest.getAddress2()+employerDetailsRequest.getDistrictName()+
//								employerDetailsRequest.getPincode()+employerDetailsRequest.getStateName()+
//								employerDetailsRequest.getGstIdentificationNumber()+employerDetailsRequest.getCreatedBy()+
//								employerDetailsRequest.getEmployerId()+employerDetailsRequest.getConsent()+
//								employerDetailsRequest.getOtpStatus()+employerDetailsRequest.getClientKey()+MessageConstant.SECRET_KEY;
				        System.out.println("first hash::"+employerDetailsRequest.getHash());
				        
//				        String hash=ValidateConstants.generateHash(dataString);
//				        System.out.println("first local hash::"+hash);
//						if(!employerDetailsRequest.getHash().equalsIgnoreCase(hash)) {
//							employerDetailsRequest.setResponse(MessageConstant.HASH_ERROR);
//							return employerDetailsRequest;
//						}
	
				        List<EmployerDetailsEntity> employerDetailsEntities=employerDetailsDao.checkEmployerOnboardingDetails(employerDetailsRequest.getOrganizationName(),employerDetailsRequest.getMobile());
						if(employerDetailsEntities!=null && employerDetailsEntities.size()>0) {
							logger.info("if");
							
							response=MessageConstant.ORG_EXIST;
							employerDetailsRequest.setResponse(response);				
						}else {
							logger.info("else");
							CopyUtility.copyProperties(employerDetailsRequest,empDetailsEntity);
							empDetailsEntity.setCreatedDate(LocalDate.now());
							//empDetailsEntity.setcre
							empDetailsEntity.setCreatedBy(employerDetailsRequest.getName());
							empDetailsEntity.setName(employerDetailsRequest.getName());
							empDetailsEntity.setOrganizationName(employerDetailsRequest.getOrganizationName());
							empDetailsEntity.setEmail(employerDetailsRequest.getEmail());
							empDetailsEntity.setMobile(employerDetailsRequest.getMobile());
							empDetailsEntity.setStatus(1);
							String employerCode=getEmployerNo();
							empDetailsEntity.setProfileComplete(1);
							empDetailsEntity.setEmployerCode(employerCode);
							EmployerDetailsEntity employerDetailsEntity2=employerDetailsDao.saveCompanyDetails(empDetailsEntity);
							if(employerDetailsEntity2!=null) {
								UserEntity userDetails= new UserEntity();
								UserEmpEntity userEmpEntity= new UserEmpEntity();
								//CopyUtility.copyProperties(userDetails, user);
								CopyUtility.copyProperties(employerDetailsRequest,userDetails);
								userDetails.setUsername(employerDetailsRequest.getName());
								userDetails.setMobile(employerDetailsRequest.getMobile());
								userDetails.setEmail(employerDetailsRequest.getEmail());
								userDetails.setEmployerid(employerDetailsEntity2.getId().intValue());
								Date date = new Date();
								LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
								userDetails.setCreated_date(LocalDate.now());
								if(employerDetailsRequest.isErupistatus()) {
									userDetails.setRole_id(MessageConstant.ERUPI_ADMIN_ROLE);
								}else {
									userDetails.setRole_id(MessageConstant.SIGN_UP_ROLE);
								}
								userDetails.setStatus(1);
								UserEntity userEntity=userDetailsDao.saveUserDetails(userDetails);
								EmployeeOnboardingRequest employeeOnboardingRequest=new EmployeeOnboardingRequest();
								employeeOnboardingRequest.setUserDetailsId(userEntity.getId());
								employeeOnboardingRequest.setEmployerId(employerDetailsEntity2.getId());
								employeeOnboardingRequest.setEmpOrCont("Employee");
								employeeOnboardingRequest.setMobile(employerDetailsRequest.getMobile());
								employeeOnboardingRequest.setEmail(employerDetailsRequest.getEmail());
								employeeOnboardingRequest.setName(employerDetailsRequest.getName());
								TokenGeneration token=new TokenGeneration();
								String tokenvalue = token.getToken(applicationConstantConfig.getTokenUrl);
								//logger.info("reputeUser::44");
								 ObjectMapper objectMapper = new ObjectMapper();
							     objectMapper.registerModule(new JavaTimeModule());
							     String json =objectMapper.writeValueAsString(employeeOnboardingRequest);
							    //    logger.info("json::100"+json);
								String response1 = CommonUtility.userRequest(tokenvalue, json,
										applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addEmployeeRepute,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
								logger.info("response1::"+response1);
								//userEmpEntity.setUser_id(UserEntity1.getId());
								userEmpEntity.setUser_id(userEntity.getId());
								userEmpEntity.setStatus(userEntity.getStatus());
								userEmpEntity.setCreated_by(userEntity.getMobile());
								userEmpEntity.setCreated_date(localDate);
								userEmpEntity=userDetailsDao.saveUserEmpEntity(userEmpEntity);
							}
							response=MessageConstant.RESPONSE_SUCCESS;
							employerDetailsRequest.setResponse(response);
						}
					} catch (Exception e) {
						// TODO: handle exception
						response=MessageConstant.RESPONSE_FAILED;
						employerDetailsRequest.setResponse(response);
					}
					return employerDetailsRequest;
		
	}


}
