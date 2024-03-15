package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeSalaryRequest;
import com.cotodel.hrms.auth.server.model.EmployeeSalaryEntity;

public interface EmployeeSalaryService {
	
	public EmployeeSalaryRequest  saveSalaryDetails(EmployeeSalaryRequest request);	
	public List<EmployeeSalaryEntity>  getEmpSalaryList(Long empid);
	public EmployeeSalaryEntity  getEmpProfile(Long employerid);
}
