package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeOnboardingFailEntity;

public interface EmployeeOnboardingFailDao {
	public EmployeeOnboardingFailEntity saveDetails(EmployeeOnboardingFailEntity employeeEntity);
	public List<EmployeeOnboardingFailEntity> getBulkFailList(Long employerId);
}
