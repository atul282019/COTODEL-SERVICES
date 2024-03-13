package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeProfileDao;
import com.cotodel.hrms.auth.server.model.EmployeeProfileEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeProfileRepository;
@Repository
public class EmployeeProfileDaoImpl implements EmployeeProfileDao{

	@Autowired
	EmployeeProfileRepository employeeProfileRepository;

	@Override
	public EmployeeProfileEntity saveDetails(EmployeeProfileEntity employeeEntity) {
		// TODO Auto-generated method stub
		return employeeProfileRepository.saveAndFlush(employeeEntity);
	}

	@Override
	public List<EmployeeProfileEntity> getEmployeeDetails(Long emplrid) {
		// TODO Auto-generated method stub
		return employeeProfileRepository.findByEmployerId(emplrid);
	}

	@Override
	public EmployeeProfileEntity getEmplDetails(Long id, Long employerid) {
		// TODO Auto-generated method stub
		return employeeProfileRepository.findEmployeeId(id, employerid);
	}
	
	
	

}
