package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingDao;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeOnboardingRepository;
@Repository
public class EmployeeOnboardingDaoImpl implements EmployeeOnboardingDao{

	@Autowired
	EmployeeOnboardingRepository employeeOnboardingRepository;	
	
	@Override
	public EmployeeOnboardingEntity saveDetails(EmployeeOnboardingEntity employeeOnboardingEntity) {		
		return employeeOnboardingRepository.saveAndFlush(employeeOnboardingEntity);
	}

	@Override
	public List<EmployeeOnboardingEntity> getEmployeeOnboardingList(Long employerId) {		
		return employeeOnboardingRepository.findByOnboardingList(employerId);
	}

}
