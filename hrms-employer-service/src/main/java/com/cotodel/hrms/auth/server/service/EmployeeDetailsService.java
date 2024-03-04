package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeDetailsRequest;
import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;

public interface EmployeeDetailsService {
	
	public EmployeeDetailsRequest  saveEmpDetails(EmployeeDetailsRequest request);
	
	public List<EmployeeDetailsEntity>  getEmpDetailsList(Long empid);
}
