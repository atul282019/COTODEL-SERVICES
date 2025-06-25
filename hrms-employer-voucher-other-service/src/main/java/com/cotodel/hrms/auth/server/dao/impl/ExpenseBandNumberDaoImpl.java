package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseBandNumberDao;
import com.cotodel.hrms.auth.server.model.ExpenseBandNumberEntity;
import com.cotodel.hrms.auth.server.repository.ExpenseBandNumberRepository;
@Repository

public class ExpenseBandNumberDaoImpl implements ExpenseBandNumberDao{

	@Autowired
	ExpenseBandNumberRepository expenseBandNumberRepository;

	@Override
	public ExpenseBandNumberEntity saveDetails(ExpenseBandNumberEntity expenseBandNumberEntity) {
		// TODO Auto-generated method stub
		return expenseBandNumberRepository.saveAndFlush(expenseBandNumberEntity);
	}

	@Override
	public ExpenseBandNumberEntity findByEmployeeBandId(Long id) {
		// TODO Auto-generated method stub
		return expenseBandNumberRepository.getById(id);
	}

	@Override
	public ExpenseBandNumberEntity findByEmployerId(Long employerId) {
		// TODO Auto-generated method stub
		return expenseBandNumberRepository.findByExpenseEmpoyerId(employerId);
	}

	@Override
	public void deleteDetails(Long id) {
		expenseBandNumberRepository.deleteById(id);
		
	}

	
}
