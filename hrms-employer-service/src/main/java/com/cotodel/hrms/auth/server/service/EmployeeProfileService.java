package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeProfileAddress;
import com.cotodel.hrms.auth.server.dto.EmployeeProfileRequest;
import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;

public interface EmployeeProfileService {
	
	public EmployeeProfileRequest  saveProfileDetails(EmployeeProfileRequest request);
	public EmployeeProfileRequest  updateProfileDetails(EmployeeProfileRequest request);
	
	public List<EmployeeProfileEntity>  getEmpProfileList(Long empid);
	public EmployeeProfileEntity  getEmpProfile(Long employerid);
	public List<EmployeeProfileAddress>  getCompProfileAddress(Long empid);
}
