package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeDetailsDao;
import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeDetailsRepository;
@Repository
public class EmployeeDetailsDaoImpl implements EmployeeDetailsDao{

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;
	
	@Override
	public EmployeeDetailsEntity saveDetails(EmployeeDetailsEntity employeeEntity) {
		
		return employeeDetailsRepository.saveAndFlush(employeeEntity);
		
	}

	@Override
	public List<EmployeeDetailsEntity> getEmployeeDetails(Long emplrid) {
		// TODO Auto-generated method stub
		return employeeDetailsRepository.findByEmployerId(emplrid);
	}

	

}
