package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingDao;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingDto;
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

	@Override
	public EmployeeOnboardingEntity getEmployeeOnboarding(String mobile) {
		
		return employeeOnboardingRepository.findByOnboarding(mobile);
	}

	@Override
	public EmployeeOnboardingEntity getEmployeeOnboardingId(Long id) {
		// TODO Auto-generated method stub
		return employeeOnboardingRepository.findByOnboardingId(id);
	}

	@Override
	public List<EmployeeOnboardingEntity> getEmployeeOnboardingManagerId(Long managerId) {
		// TODO Auto-generated method stub
		return employeeOnboardingRepository.findByOnboardingManagerId(managerId);
	}

	@Override
	public EmployeeOnboardingEntity getEmployeeOnboardingUserId(Long userId) {
		// TODO Auto-generated method stub
		return employeeOnboardingRepository.findByOnboardingUserId(userId);
	}

	@Override
	public List<EmployeeOnboardingEntity> getReputeEmployeeOnboarding() {
		// TODO Auto-generated method stub
		return employeeOnboardingRepository.findByOnboardingReputeManagerId();
	}

	@Override
	public List<EmployeeOnboardingDto> getDriverEmployeeOnboarding(Long orgId,String name) {
		// TODO Auto-generated method stub
		return employeeOnboardingRepository.findByDriverOnboardingList(orgId,name);
	}

	
}
