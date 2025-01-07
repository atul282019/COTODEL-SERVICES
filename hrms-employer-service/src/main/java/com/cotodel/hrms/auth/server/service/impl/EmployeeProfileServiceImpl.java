package com.cotodel.hrms.auth.server.service.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeDao;
import com.cotodel.hrms.auth.server.dao.EmployeeProfileDao;
import com.cotodel.hrms.auth.server.dao.EmployerDao;
import com.cotodel.hrms.auth.server.dto.EmployeeProfileAddress;
import com.cotodel.hrms.auth.server.dto.EmployeeProfileRequest;
import com.cotodel.hrms.auth.server.model.EmployeeEntity;
import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;
import com.cotodel.hrms.auth.server.model.EmployerEntity;
import com.cotodel.hrms.auth.server.service.EmployeeProfileService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@Repository
public class EmployeeProfileServiceImpl implements EmployeeProfileService{
	
	@Autowired
	EmployerDao  employerDao;
	
	
	
	@Autowired
	EmployeeDao  employeeDao;
	
	@Autowired
	EmployeeProfileDao  emplProfileDao;
	
	
//	@Override
//	public EmployeeProfileRequest saveProfileDetails(EmployeeProfileRequest user) {
//		EmployerEntity employerEntity=null;
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			user.setResponse(response);
//		
//		SignUpEntity signUpEntity=new SignUpEntity();
//		signUpEntity.setOrgType(user.getOrganizationType());
//		signUpDao.saveUserDetails(signUpEntity);
//		user.setSignupId(signUpEntity.getSignupId());
//		EmployerEntity employer=new EmployerEntity();
//		employer.setSignup(signUpEntity);		
//		employer=getEmployerDeails(employer,user);
//		employerEntity=employerDao.saveDetails(employer);
//		user.setEmployerId(employerEntity.getEmployerId());
//		//
//		EmployeeEntity employee=new EmployeeEntity();
//		employee.setEmployer(employer);
//		employee.setPan(user.getPan());
//		
//		employee=employeeDao.saveDetails(employee);
//		user.setEmployeeId(employee.getEmployeeId());
//
//		response=MessageConstant.RESPONSE_SUCCESS;
//		user.setResponse(response);
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			user.setResponse(response);
//		}
//		return user;
//	}
	@Override
	public EmployeeProfileRequest saveProfileDetails(EmployeeProfileRequest user) {
		EmployerEntity employerEntity=null;
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			user.setResponse(response);
		
		EmployeeProfileEntity employeeProfileEntity=new EmployeeProfileEntity();
		CopyUtility.copyProperties(user,employeeProfileEntity);
		employeeProfileEntity.setProfileComplete(2);
		employeeProfileEntity=emplProfileDao.saveDetails(employeeProfileEntity);
		
//		SignUpEntity signUpEntity=new SignUpEntity();
//		signUpEntity.setOrgType(user.getOrganizationType());
//		signUpDao.saveUserDetails(signUpEntity);
//		user.setSignupId(signUpEntity.getSignupId());
//		EmployerEntity employer=new EmployerEntity();
//		employer.setSignup(signUpEntity);		
//		employer=getEmployerDeails(employer,user);
//		employerEntity=employerDao.saveDetails(employer);
//		user.setEmployerId(employerEntity.getEmployerId());
//		//
//		EmployeeEntity employee=new EmployeeEntity();
//		employee.setEmployer(employer);
//		employee.setPan(user.getPan());
//		
//		employee=employeeDao.saveDetails(employee);
		
		user.setEmployeeId(employeeProfileEntity.getId());
		

		response=MessageConstant.RESPONSE_SUCCESS;
		user.setResponse(response);
		}catch (DataIntegrityViolationException e) {
			response=MessageConstant.DUP_PAN;
			user.setResponse(response);
		}
		 catch (Exception e) {
			//response=MessageConstant.RESPONSE_FAILED;
			//user.setResponse(response);
			 
		}
		return user;
	}

	@Override
	public EmployeeProfileRequest updateProfileDetails(EmployeeProfileRequest user) {
		EmployerEntity employerEntity=null;
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			user.setResponse(response);
			
		//Date date = new Date();
		//LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
//		SignUpEntity signUpEntity=signUpDao.getUser(user.getSignupId());
//		signUpEntity.setOrgType(user.getOrgType());
//		signUpDao.saveUserDetails(signUpEntity);
//		user.setSignupId(signUpEntity.getSignupId());
		EmployerEntity employer=employerDao.getUser(user.getEmployerId());
		
		employer.setRunPayrollFlag(user.isRunPayrollFlag());
		employer.setSalaryAdvancesFlag(user.isSalaryAdvancesFlag());
		
		employerEntity=employerDao.saveDetails(employer);
		user.setEmployerId(employerEntity.getEmployerId());
		//
		EmployeeEntity employee=employeeDao.getUser(user.getEmployeeId());
		employee.setEmployer(employer);
		employee.setPan(user.getPan());
		
		employee=employeeDao.saveDetails(employee);
		user.setEmployeeId(employee.getEmployeeId());

		response=MessageConstant.RESPONSE_SUCCESS;
		user.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			user.setResponse(response);
		}
		return user;
	}
	public EmployerEntity getEmployerDeails(EmployerEntity employer,EmployeeProfileRequest user) {
		employer.setOrgType(user.getOrgType());
		employer.setGstin(user.getGstnNo());
		employer.setPan(user.getPan());
		employer.setPanDetails(user.getPanDetails());
		employer.setCompanyName(user.getCompanyName());
		employer.setOfficeAddress(user.getOfficeAddress());
		employer.setAddressLine(user.getAddressLine());
		employer.setPinCode(user.getPinCode());
		employer.setStateCode(user.getStateCode());
		employer.setPayrollEnabledFlag(user.isPayrollEnabledFlag());
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(user.getPaidDate());
		Date date=null;
		try {
			date=formatter.parse(user.getPaidDate());
		} catch (Exception e) {
			
		}
		employer.setPaidDate(date);
		employer.setRunPayrollFlag(user.isRunPayrollFlag());
		employer.setSalaryAdvancesFlag(user.isSalaryAdvancesFlag());       
	return employer;
}

	@Override
	public List<EmployeeProfileEntity> getEmpProfileList(Long empid) {
		// TODO Auto-generated method stub
		return emplProfileDao.getEmployeeDetails(empid);
	}

	@Override
	public EmployeeProfileEntity getEmpProfile( Long employerid) {
		// TODO Auto-generated method stub
		return emplProfileDao.getEmplDetails( employerid);
	}

	@Override
	public EmployeeProfileAddress getCompProfileAddress(Long empid) {
		// TODO Auto-generated method stub
		EmployeeProfileAddress employeeProfileAddresses=new EmployeeProfileAddress();
		List<Object[]> addresses= emplProfileDao.getCompAddress(empid);
		for (Object[] objects : addresses) {
			 Long id = ((BigInteger) objects[0]).longValue();			           
	         String address = (String) objects[1]; 
	         String pin = (String) objects[2]; 
	         String officeAddress=address+"-"+pin;
	         employeeProfileAddresses.setId(id);
	         employeeProfileAddresses.setOfficeAddress(officeAddress);
		}
		
		return employeeProfileAddresses;
	}
	
	
	
}
