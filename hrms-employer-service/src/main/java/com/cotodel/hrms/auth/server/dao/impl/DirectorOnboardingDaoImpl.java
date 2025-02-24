package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.DirectorOnboardingDao;
import com.cotodel.hrms.auth.server.model.DirectorOnboardingEntity;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.repository.DirectorOnboardingRepository;
import com.cotodel.hrms.auth.server.repository.EmployeeOnboardingRepository;
@Repository
public class DirectorOnboardingDaoImpl implements DirectorOnboardingDao{

	@Autowired
	DirectorOnboardingRepository directorOnboardingRepository;

	@Override
	public DirectorOnboardingEntity saveDetails(DirectorOnboardingEntity employeeEntity) {
		// TODO Auto-generated method stub
		return directorOnboardingRepository.saveAndFlush(employeeEntity);
	}

	@Override
	public List<DirectorOnboardingEntity> getEmployeeOnboardingList(Long employerId) {
		// TODO Auto-generated method stub
		return directorOnboardingRepository.findByOnboardingList(employerId);
	}	
	
	
	
}
