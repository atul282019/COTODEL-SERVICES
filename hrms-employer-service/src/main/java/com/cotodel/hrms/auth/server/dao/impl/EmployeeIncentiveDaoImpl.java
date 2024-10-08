package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeIncentiveDao;
import com.cotodel.hrms.auth.server.model.EmployeeIncentiveEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeIncentiveRepository;
@Repository
public class EmployeeIncentiveDaoImpl implements EmployeeIncentiveDao{

	@Autowired
	EmployeeIncentiveRepository employeeIncentiveRepository;

	@Override
	public EmployeeIncentiveEntity saveDetails(EmployeeIncentiveEntity employeeEntity) {
		// TODO Auto-generated method stub
		return employeeIncentiveRepository.saveAndFlush(employeeEntity);
	}

	@Override
	public List<EmployeeIncentiveEntity> getEmployeeIncentive(Long emplrid) {
		// TODO Auto-generated method stub
		return employeeIncentiveRepository.findByEmployerId(emplrid);
	}

	@Override
	public EmployeeIncentiveEntity getEmplIncentive(Long employerid) {
		// TODO Auto-generated method stub
		return employeeIncentiveRepository.findEmployeeId(employerid);
	}

	

		
	
	

}
