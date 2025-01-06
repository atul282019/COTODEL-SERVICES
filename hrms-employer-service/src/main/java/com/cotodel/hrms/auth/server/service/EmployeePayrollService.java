package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.EmployeePayrollRequest;
import com.cotodel.hrms.auth.server.dto.EmployeePayrollTaxRequest;
import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;

public interface EmployeePayrollService {
	
	public EmployeePayrollRequest  saveEmployeePayrollDetails(EmployeePayrollRequest request);
	public EmployeePayrollTaxRequest  saveEmployeePayrollTaxDetails(EmployeePayrollTaxRequest request);
	public EmployeePayrollRequest  saveEmployeePayrollDetailsNew(EmployeePayrollRequest request);
	public EmployeeProfileEntity getEmployerPayRollDetail(Long employerId);
}
