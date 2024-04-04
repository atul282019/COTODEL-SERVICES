package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

public interface EmployeeOnboardingService {
	
	public EmployeeOnboardingRequest  saveEmployeeDetails(EmployeeOnboardingRequest	 request);
	
	public EmployeeOnboardingRequest  saveBulkEmployeeDetails(EmployeeOnboardingRequest	 request);
	public List<EmployeeOnboardingEntity>  getEmployeeDetailsList(Long employerid);
	public List<EmployeeOnboardingRequest>  confirmBulkEmployeeDetails(List<EmployeeOnboardingRequest>	 request);
}
