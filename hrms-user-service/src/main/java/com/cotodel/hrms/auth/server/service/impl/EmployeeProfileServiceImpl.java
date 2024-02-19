package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployerDao;
import com.cotodel.hrms.auth.server.dao.OrganizationDao;
import com.cotodel.hrms.auth.server.dao.SignUpDao;
import com.cotodel.hrms.auth.server.dto.EmployeeProfileRequest;
import com.cotodel.hrms.auth.server.entity.EmployerEntity;
import com.cotodel.hrms.auth.server.entity.SignUpEntity;
import com.cotodel.hrms.auth.server.entity.UserEmpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.service.EmployeeProfileService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
@Repository
public class EmployeeProfileServiceImpl implements EmployeeProfileService{
	
	@Autowired
	EmployerDao  employerDao;
	
	@Autowired
	SignUpDao  signUpDao;
	
	@Transactional
	@Override
	public SignUpEntity saveProfileDetails(EmployeeProfileRequest user) {
		UserEntity userDetails= new UserEntity();
		EmployerDao employerDao=null;
		UserEmpEntity userEmpEntity= new UserEmpEntity();
		//CopyUtility.copyProperties(userDetails, user);
		//CopyUtility.copyProperties(user,userDetails);
		Date date = new Date();
		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		userDetails.setCreated_date(localDate);
		
		SignUpEntity signUpEntity=new SignUpEntity();
		signUpEntity.setOrgType(user.getOrganization_type());
		signUpDao.saveUserDetails(signUpEntity);
		
		EmployerEntity employer=new EmployerEntity();
		employer.setSignup(signUpEntity);
		employer.setOrgType(user.getOrganization_type());
		employerDao=employerDao.saveDetails(employer);
		
		return signUpEntity;
	}

}
