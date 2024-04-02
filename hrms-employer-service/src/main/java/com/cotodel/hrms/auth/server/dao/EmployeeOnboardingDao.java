package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

public interface EmployeeOnboardingDao {
	public EmployeeOnboardingEntity saveDetails(EmployeeOnboardingEntity employeeEntity);
	//public EmployeeOnboardingEntity getEmployeeOnboarding(Long employeeId);
	public List<EmployeeOnboardingEntity> getEmployeeOnboardingList(Long employerId);
}
