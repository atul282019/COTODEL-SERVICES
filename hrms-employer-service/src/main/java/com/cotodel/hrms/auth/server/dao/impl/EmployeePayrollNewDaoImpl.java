package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeePayrollNewDao;
import com.cotodel.hrms.auth.server.model.EmployeePayrollNewEntity;
import com.cotodel.hrms.auth.server.repository.EmployeePayrollNewRepository;
@Repository
public class EmployeePayrollNewDaoImpl implements EmployeePayrollNewDao{

	@Autowired
	EmployeePayrollNewRepository employeePayrollNewRepository;
	
	@Override
	public EmployeePayrollNewEntity saveDetails(EmployeePayrollNewEntity employeeEntity) {
		
		return employeePayrollNewRepository.saveAndFlush(employeeEntity);
		
	}


}
