package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.Employee;
import com.cotodel.hrms.auth.server.dto.EmployeeRequest;

public interface EmployeeService {
	
	public EmployeeRequest  saveEmployeeDetails(EmployeeRequest	 request);
	
	public EmployeeRequest  getEmployeeDetails(Long	 empid);
	
	public List<Employee>  getEmployeeDetailsList();
	
}
