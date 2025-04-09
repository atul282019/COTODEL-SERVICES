package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployerDetailsRequest;
import com.cotodel.hrms.auth.server.dto.EmployerProfileAddress;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;

public interface EmployerDetailsService {
	
	public EmployerDetailsRequest  saveEmployerDetails(EmployerDetailsRequest user);
	public EmployerDetailsEntity  getEmployerDetails(Long employerId);
	public EmployerDetailsEntity  getEmployerComplete(Long employerId);
	public List<EmployerProfileAddress>  getCompaneyAddress(Long employerId);
	public EmployerDetailsRequest  updateEmployerDetails(EmployerDetailsRequest user);
	
}
