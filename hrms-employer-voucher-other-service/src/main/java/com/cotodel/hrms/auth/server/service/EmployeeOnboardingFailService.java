package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;

public interface EmployeeOnboardingFailService {	
	
	//public EmployeeOnboardingRequest  saveBulkFailEmployeeDetails(EmployeeOnboardingRequest	 request);
	public List<EmployeeOnboardingRequest>  getBulkFailDetailsList(Long employerid);
	
}
