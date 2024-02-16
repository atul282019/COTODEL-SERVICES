package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.EmployeeProfileRequest;
import com.cotodel.hrms.auth.server.entity.SignUpEntity;

public interface EmployeeProfileService {
	
	public SignUpEntity saveProfileDetails(EmployeeProfileRequest user);
	
}
