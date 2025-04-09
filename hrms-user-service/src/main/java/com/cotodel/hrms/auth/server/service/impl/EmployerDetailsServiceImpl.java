/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.EmployerDetailsDao;
import com.cotodel.hrms.auth.server.dao.UserDetailsDao;
import com.cotodel.hrms.auth.server.dto.EmployerDetailsRequest;
import com.cotodel.hrms.auth.server.dto.EmployerProfileAddress;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.service.EmployerDetailsService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.SQLInjectionValidator;
import com.cotodel.hrms.auth.server.util.ValidateConstants;

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

	@Override
	public EmployerDetailsRequest saveEmployerDetails(EmployerDetailsRequest employerDetailsRequest) {
		// TODO Auto-generated method stub
		logger.info(" inside saveEmployerDetails");
		EmployerDetailsEntity employerDetailsEntity= new EmployerDetailsEntity();
		EmployerDetailsEntity empDetailsEntity= new EmployerDetailsEntity();
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
//			
	        String dataString =employerDetailsRequest.getLegalNameOfBusiness()+employerDetailsRequest.getTradeName()+employerDetailsRequest.getConstitutionOfBusiness()+
	        		employerDetailsRequest.getOrgType()+employerDetailsRequest.getAddress1()+employerDetailsRequest.getAddress2()+
	        		employerDetailsRequest.getDistrictName()+employerDetailsRequest.getPincode()+employerDetailsRequest.getStateName()+
	        		employerDetailsRequest.getGstIdentificationNumber()+employerDetailsRequest.getPan()+employerDetailsRequest.getCreatedBy()+
	        		employerDetailsRequest.getEmployerId()+employerDetailsRequest.getClientKey()+MessageConstant.SECRET_KEY;
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
				CopyUtility.copyProperties(employerDetailsRequest,empDetailsEntity);
				empDetailsEntity.setId(employerDetailsEntity.getId());
				empDetailsEntity.setEmployerId(employerDetailsEntity.getEmployerId());
				empDetailsEntity.setEmployerCode(employerDetailsEntity.getEmployerCode());
				empDetailsEntity.setCreatedBy(employerDetailsEntity.getCreatedBy());
				empDetailsEntity.setCreatedDate(employerDetailsEntity.getCreatedDate());
				empDetailsEntity.setUpdatedDate(LocalDate.now());
				empDetailsEntity.setStatus(1);
				if(employerDetailsRequest.getOtpStatus()!=null && employerDetailsRequest.getOtpStatus().equalsIgnoreCase("YES")) {
					empDetailsEntity.setProfileComplete(3);
				}else {
					empDetailsEntity.setProfileComplete(2);
				}
				//empDetailsEntity.setProfileComplete(3);
				empDetailsEntity.setConsent(employerDetailsRequest.getConsent());
				empDetailsEntity.setOtpStatus(employerDetailsRequest.getOtpStatus());
				empDetailsEntity.setUpdatedBy(employerDetailsRequest.getCreatedBy());
				employerDetailsDao.saveCompanyDetails(empDetailsEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
				employerDetailsRequest.setResponse(response);				
			}else {
				logger.info("else");
				CopyUtility.copyProperties(employerDetailsRequest,empDetailsEntity);
				empDetailsEntity.setCreatedDate(LocalDate.now());
				//empDetailsEntity.setcre
				empDetailsEntity.setStatus(1);
				String employerCode=getEmployerNo();
				empDetailsEntity.setProfileComplete(2);
				empDetailsEntity.setEmployerCode(employerCode);
				employerDetailsDao.saveCompanyDetails(empDetailsEntity);		
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
	int comp=0;
	response=MessageConstant.RESPONSE_FAILED;
	try {			
		employerDetailsEntity=employerDetailsDao.getEmployerDetails(employerId);
		if(employerDetailsEntity!=null) {
			comp=employerDetailsEntity.getProfileComplete();
		}else {
			UserEntity userEntity=userDetailsDao.getOrgExist(employerId);
			if(userEntity!=null) {
				int role=userEntity.getRole_id();
				if(role==1 || role==9) {
					employerDetailsEntity= new EmployerDetailsEntity();
					employerDetailsEntity.setEmployerId(employerId);
					employerDetailsEntity.setProfileComplete(1);
				}
			}
		}
		
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
				
				String dataString =employerDetailsRequest.getLegalNameOfBusiness()+employerDetailsRequest.getTradeName()+
						employerDetailsRequest.getConstitutionOfBusiness()+employerDetailsRequest.getOrgType()+
						employerDetailsRequest.getAddress1()+employerDetailsRequest.getAddress2()+
						employerDetailsRequest.getDistrictName()+employerDetailsRequest.getPincode()+
						employerDetailsRequest.getStateName()+employerDetailsRequest.getGstIdentificationNumber()+
						employerDetailsRequest.getPan()+employerDetailsRequest.getCreatedBy()+
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
					CopyUtility.copyProperties(employerDetailsRequest,empDetailsEntity);
					empDetailsEntity.setId(employerDetailsEntity.getId());
					empDetailsEntity.setEmployerId(employerDetailsEntity.getEmployerId());
					empDetailsEntity.setEmployerCode(employerDetailsEntity.getEmployerCode());
					empDetailsEntity.setCreatedBy(employerDetailsEntity.getCreatedBy());
					empDetailsEntity.setCreatedDate(employerDetailsEntity.getCreatedDate());
					empDetailsEntity.setUpdatedDate(LocalDate.now());
					empDetailsEntity.setStatus(1);
					if(employerDetailsRequest.getOtpStatus()!=null && employerDetailsRequest.getOtpStatus().equalsIgnoreCase("YES")) {
						empDetailsEntity.setProfileComplete(3);
					}else {
						empDetailsEntity.setProfileComplete(2);
					}
					//empDetailsEntity.setProfileComplete(3);
					empDetailsEntity.setConsent(employerDetailsRequest.getConsent());
					empDetailsEntity.setOtpStatus(employerDetailsRequest.getOtpStatus());
					empDetailsEntity.setUpdatedBy(employerDetailsRequest.getCreatedBy());
					employerDetailsDao.saveCompanyDetails(empDetailsEntity);
					response=MessageConstant.RESPONSE_SUCCESS;
					employerDetailsRequest.setResponse(response);				
				}else {
					logger.info("else");
					CopyUtility.copyProperties(employerDetailsRequest,empDetailsEntity);
					empDetailsEntity.setCreatedDate(LocalDate.now());
					//empDetailsEntity.setcre
					empDetailsEntity.setStatus(1);
					String employerCode=getEmployerNo();
					empDetailsEntity.setProfileComplete(2);
					empDetailsEntity.setEmployerCode(employerCode);
					employerDetailsDao.saveCompanyDetails(empDetailsEntity);		
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
