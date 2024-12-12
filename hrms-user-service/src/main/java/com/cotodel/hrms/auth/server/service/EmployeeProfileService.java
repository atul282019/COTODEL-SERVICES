package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.EmployeeProfileRequest;

public interface EmployeeProfileService {
	
	public String  saveProfileDetails(EmployeeProfileRequest user);
	
}
