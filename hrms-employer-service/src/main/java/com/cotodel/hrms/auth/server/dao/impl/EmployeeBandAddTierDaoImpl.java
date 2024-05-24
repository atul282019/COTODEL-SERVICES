package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeBandAddTierDao;
import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierEntity;
import com.cotodel.hrms.auth.server.model.EmployeeBandEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeBandAddTierRepository;
@Repository
public class EmployeeBandAddTierDaoImpl implements EmployeeBandAddTierDao{

	@Autowired
	EmployeeBandAddTierRepository employeeBandAddTierRepository;

	@Override
	public List<EmployeeBandAddTierEntity> saveDetails(List<EmployeeBandAddTierEntity> employeeBandAddTierEntity) {
		// TODO Auto-generated method stub
		return employeeBandAddTierRepository.saveAllAndFlush(employeeBandAddTierEntity);
	}

	@Override
	public List<EmployeeBandAddTierEntity> getDetails(Long bandtierId) {
		// TODO Auto-generated method stub
		return employeeBandAddTierRepository.getBandAddTier(bandtierId);
	}

	
	
	


	

	
}
