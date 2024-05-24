package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeBandAddTierReviewDao;
import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierReviewEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeBandAddTierReviewRepository;
@Repository
public class EmployeeBandAddTierReviewDaoImpl implements EmployeeBandAddTierReviewDao{

	@Autowired
	EmployeeBandAddTierReviewRepository employeeBandAddTierReviewRepository;

	@Override
	public List<EmployeeBandAddTierReviewEntity> saveDetails(List<EmployeeBandAddTierReviewEntity> employeeBandAddTierEntity) {
		// TODO Auto-generated method stub
		return employeeBandAddTierReviewRepository.saveAllAndFlush(employeeBandAddTierEntity);
	}

	
	
	


	

	
}
