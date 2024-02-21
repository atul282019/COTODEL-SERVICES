package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.EmployeeProfileRequest;

public interface EmployeeProfileService {
	
	public EmployeeProfileRequest  saveProfileDetails(EmployeeProfileRequest request);
	public EmployeeProfileRequest  updateProfileDetails(EmployeeProfileRequest request);
	
}
