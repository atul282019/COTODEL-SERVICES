package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CompanyEmployeeDao;
import com.cotodel.hrms.auth.server.model.CompanyEmployeeEntity;
import com.cotodel.hrms.auth.server.repository.CompanyEmployeeRepository;
@Repository
public class CompanyEmployeeDaoImpl implements CompanyEmployeeDao{

	@Autowired
	CompanyEmployeeRepository companyEmployeeRepository;

	@Override
	public CompanyEmployeeEntity saveDetails(CompanyEmployeeEntity employeePayrollEntity) {
		// TODO Auto-generated method stub
		return companyEmployeeRepository.saveAndFlush(employeePayrollEntity);
	}

	@Override
	public CompanyEmployeeEntity getCompanyEmployee(Long employeeId) {
		
		return companyEmployeeRepository.findByCompanyEmployeeEntity(employeeId);
	}
	
	


}
