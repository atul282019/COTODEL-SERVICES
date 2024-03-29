package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployerDao;
import com.cotodel.hrms.auth.server.model.EmployerEntity;
import com.cotodel.hrms.auth.server.repository.EmployerRepository;
@Repository
public class EmployerDaoImpl implements EmployerDao{

	@Autowired
	EmployerRepository employerRepository;
	
	@Override
	public EmployerEntity saveDetails(EmployerEntity employerEntity) {
		
		return employerRepository.saveAndFlush(employerEntity);
	}

	@Override
	public EmployerEntity getUser(Long employerId) {
		// TODO Auto-generated method stub
		return employerRepository.findByEmployerId(employerId);
	}
	
	

}
