package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeDao;
import com.cotodel.hrms.auth.server.dao.EmployerDao;
import com.cotodel.hrms.auth.server.dao.OrganizationDao;
import com.cotodel.hrms.auth.server.dao.SignUpDao;
import com.cotodel.hrms.auth.server.dto.EmployeeProfileRequest;
import com.cotodel.hrms.auth.server.entity.EmployeeEntity;
import com.cotodel.hrms.auth.server.entity.EmployerEntity;
import com.cotodel.hrms.auth.server.entity.SignUpEntity;
import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.service.EmployeeProfileService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeProfileServiceImpl implements EmployeeProfileService{
	
	@Autowired
	EmployerDao  employerDao;
	
	@Autowired
	SignUpDao  signUpDao;
	
	@Autowired
	EmployeeDao  employeeDao;
	
	@Transactional
	@Override
	public String saveProfileDetails(EmployeeProfileRequest user) {
		UserEntity userDetails= new UserEntity();
		EmployerEntity employerEntity=null;
		String response="";
		try {
			
		
		UserEmpEntity userEmpEntity= new UserEmpEntity();
		//CopyUtility.copyProperties(userDetails, user);
		Date date = new Date();
		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		userDetails.setCreated_date(localDate);
		
		SignUpEntity signUpEntity=new SignUpEntity();
		signUpEntity.setOrgType(user.getOrganizationType());
		signUpDao.saveUserDetails(signUpEntity);
		
		EmployerEntity employer=new EmployerEntity();
		employer.setSignup(signUpEntity);
		employer.setOrgType(user.getOrganizationType());
		employerEntity=employerDao.saveDetails(employer);
		
		//
		EmployeeEntity employee=new EmployeeEntity();
		//employer.setSignup(signUpEntity);
		//employer.setOrgType(user.getOrganizationType());
		employee=employeeDao.saveDetails(employee);
		response=MessageConstant.RESPONSE_SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			response=MessageConstant.RESPONSE_FAILED;
		}
		return response;
	}

}
