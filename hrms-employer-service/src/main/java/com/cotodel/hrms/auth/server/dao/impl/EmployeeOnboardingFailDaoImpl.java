package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingFailDao;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingFailEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeOnboardingFailRepository;
@Repository
public class EmployeeOnboardingFailDaoImpl implements EmployeeOnboardingFailDao{

	@Autowired
	EmployeeOnboardingFailRepository employeeOnboardingFailRepository;	
	
	
	
	@Override
	public EmployeeOnboardingFailEntity saveDetails(EmployeeOnboardingFailEntity employeeEntity) {
		// TODO Auto-generated method stub
		return employeeOnboardingFailRepository.saveAndFlush(employeeEntity);
		
	}

}
