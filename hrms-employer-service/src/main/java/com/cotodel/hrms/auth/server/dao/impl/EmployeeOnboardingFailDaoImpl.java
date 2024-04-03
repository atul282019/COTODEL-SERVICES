package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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



	@Override
	@Transactional
	public List<EmployeeOnboardingFailEntity> getBulkFailList(Long employerId) {
		
		return employeeOnboardingFailRepository.findByOnboardingFailList(employerId);
	}

	
}
