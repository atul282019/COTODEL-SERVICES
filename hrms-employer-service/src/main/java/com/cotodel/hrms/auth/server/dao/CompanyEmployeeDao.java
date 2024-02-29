package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.CompanyEmployeeEntity;

public interface CompanyEmployeeDao {
	public CompanyEmployeeEntity saveDetails(CompanyEmployeeEntity companyEmployeeEntity);
	public CompanyEmployeeEntity getCompanyEmployee(Long employeeId);
	
}
