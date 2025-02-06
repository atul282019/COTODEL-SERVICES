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

/**
 * 
 */

@Service
public class EmployerDetailsServiceImpl implements EmployerDetailsService {

	@Autowired
	public EmployerDetailsDao employerDetailsDao;
	
	@Autowired
	public UserDetailsDao userDetailsDao;
	
	@Autowired
    private EntityManager entityManager;

	@Override
	public EmployerDetailsRequest saveEmployerDetails(EmployerDetailsRequest employerDetailsRequest) {
		// TODO Auto-generated method stub

		EmployerDetailsEntity employerDetailsEntity= new EmployerDetailsEntity();
		EmployerDetailsEntity empDetailsEntity= new EmployerDetailsEntity();
		String response="";
		response=MessageConstant.RESPONSE_FAILED;
		employerDetailsRequest.setResponse(response);
		try {			
			employerDetailsEntity=employerDetailsDao.getEmployerDetails(employerDetailsRequest.getEmployerId());
			if(employerDetailsEntity!=null) {
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


}
