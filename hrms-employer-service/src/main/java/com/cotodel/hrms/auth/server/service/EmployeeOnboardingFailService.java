package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;

public interface EmployeeOnboardingFailService {	
	
	public EmployeeOnboardingRequest  saveBulkFailEmployeeDetails(EmployeeOnboardingRequest	 request);
	//public List<EmployeeOnboardingEntity>  getEmployeeDetailsList(Long employerid);
	
}
