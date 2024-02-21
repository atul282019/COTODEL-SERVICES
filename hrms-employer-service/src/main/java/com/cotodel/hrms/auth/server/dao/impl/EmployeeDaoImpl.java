package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeDao;
import com.cotodel.hrms.auth.server.model.EmployeeEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeRepository;
@Repository
public class EmployeeDaoImpl implements EmployeeDao{

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeEntity saveDetails(EmployeeEntity employeeEntity) {
		
		return employeeRepository.saveAndFlush(employeeEntity);
		
	}

	@Override
	public EmployeeEntity getUser(Long employeeId) {
		// TODO Auto-generated method stub
		return employeeRepository.findByEmployeeId(employeeId);
	}
	

}
