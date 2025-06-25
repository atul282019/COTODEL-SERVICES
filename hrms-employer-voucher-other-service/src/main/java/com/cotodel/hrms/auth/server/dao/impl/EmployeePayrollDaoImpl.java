package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeePayrollDao;
import com.cotodel.hrms.auth.server.model.EmployeePayrollEntity;
import com.cotodel.hrms.auth.server.repository.EmployeePayrollRepository;
@Repository
public class EmployeePayrollDaoImpl implements EmployeePayrollDao{

	@Autowired
	EmployeePayrollRepository employeePayrollRepository;
	
	@Override
	public EmployeePayrollEntity saveDetails(EmployeePayrollEntity employeeEntity) {
		
		return employeePayrollRepository.saveAndFlush(employeeEntity);
		
	}


}
