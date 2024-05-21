package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseCategoryBandDao;
import com.cotodel.hrms.auth.server.model.ExpenseCategoryBandEntity;
import com.cotodel.hrms.auth.server.repository.ExpenseCategoryBandRepository;
@Repository
public class ExpenseCategoryBandDaoImpl implements ExpenseCategoryBandDao{

	@Autowired
	ExpenseCategoryBandRepository expenseCategoryBandRepository;

	@Override
	public ExpenseCategoryBandEntity saveDetails(ExpenseCategoryBandEntity employeeBandEntity) {
		
		return expenseCategoryBandRepository.saveAndFlush(employeeBandEntity);
	}
	

	@Override
	public ExpenseCategoryBandEntity getEmployeeBandDetails(String bandId) {
		
		return expenseCategoryBandRepository.findByEmployeeBandId(bandId);
	}


	@Override
	public ExpenseCategoryBandEntity findByEmployeeBandId() {
		// TODO Auto-generated method stub
		return expenseCategoryBandRepository.findByEmployeeBandId();
	}


	@Override
	public ExpenseCategoryBandEntity findByEmployeeBandId(String expenseCode) {
		// TODO Auto-generated method stub
		return expenseCategoryBandRepository.findByEmployeeExpenseCode(expenseCode);
	}
	

}
