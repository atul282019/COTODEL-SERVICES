package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeBandDao;
import com.cotodel.hrms.auth.server.model.EmployeeBandEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeBandRepository;
@Repository
public class EmployeeBandDaoImpl implements EmployeeBandDao{

	@Autowired
	EmployeeBandRepository employeeBandRepository;

	@Override
	public EmployeeBandEntity saveDetails(EmployeeBandEntity employeeBandEntity) {
		
		return employeeBandRepository.saveAndFlush(employeeBandEntity);
	}

//	@Override
//	public EmployeeBandEntity getEmployeeBandDetails(String bandId) {
//		
//		return employeeBandRepository.findByEmployeeBandId(bandId);
//	}

	@Override
	public List<EmployeeBandEntity> getEmployeeBandList() {
		// TODO Auto-generated method stub
		return employeeBandRepository.findByEmployeeBandList();
	}

	@Override
	public EmployeeBandEntity getEmployeeBandId(Long employerId) {
		// TODO Auto-generated method stub
		return employeeBandRepository.findByEmployeeBand(employerId);
	}
	
	
}
