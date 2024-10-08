package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeePayrollTaxDao;
import com.cotodel.hrms.auth.server.model.EmployeePayrollTaxEntity;
import com.cotodel.hrms.auth.server.repository.EmployeePayrollTaxRepository;
@Repository
public class EmployeePayrollTaxDaoImpl implements EmployeePayrollTaxDao{

	@Autowired
	EmployeePayrollTaxRepository employeePayrollTaxRepository;

	@Override
	public EmployeePayrollTaxEntity saveDetails(EmployeePayrollTaxEntity employeePayrollTaxEntity) {
		// TODO Auto-generated method stub
		return employeePayrollTaxRepository.saveAndFlush(employeePayrollTaxEntity);
	}

	
	

}
