package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployerDao;
import com.cotodel.hrms.auth.server.entity.EmployerEntity;
import com.cotodel.hrms.auth.server.repository.EmployerRepository;
import com.cotodel.hrms.auth.server.repository.SignUpRepository;
@Repository
public class EmployerDaoImpl implements EmployerDao{

	@Autowired
	EmployerRepository employerRepository;
	
	@Override
	public EmployerEntity saveDetails(EmployerEntity employerEntity) {
		
		return employerRepository.saveAndFlush(employerEntity);
	}

}
