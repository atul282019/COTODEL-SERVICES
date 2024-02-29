package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.CompanyEmployeeRequest;

public interface CompanyEmployeeService {
	
	public CompanyEmployeeRequest  saveCompEmployeeDetails(CompanyEmployeeRequest request);
	public CompanyEmployeeRequest  getCompEmployeeDetails(Long employeeid);
}
