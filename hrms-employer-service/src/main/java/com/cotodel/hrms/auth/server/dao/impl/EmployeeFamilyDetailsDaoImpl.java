package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeFamilyDetailsDao;
import com.cotodel.hrms.auth.server.model.EmployeeFamilyDetailEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeFamilyDetailsRepository;
@Repository
public class EmployeeFamilyDetailsDaoImpl implements EmployeeFamilyDetailsDao{

	@Autowired
	EmployeeFamilyDetailsRepository employeeFamilyDetailsRepository;
	
	@Override
	public EmployeeFamilyDetailEntity saveDetails(EmployeeFamilyDetailEntity employeeEntity) {
		
		return employeeFamilyDetailsRepository.saveAndFlush(employeeEntity);
		
	}

	@Override
	public List<EmployeeFamilyDetailEntity> getEmployeeDetails(Long emplrid) {
		// TODO Auto-generated method stub
		return employeeFamilyDetailsRepository.findByEmployerId(emplrid);
	}

	

}
