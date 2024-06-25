package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseReimbursementDao;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementResp;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;
import com.cotodel.hrms.auth.server.repository.ExpenseReimbursementRepository;
@Repository

public class ExpenseReimbursementDaoImpl implements ExpenseReimbursementDao{

	@Autowired
	ExpenseReimbursementRepository expenseReimbursementRepository;

	@Override
	public ExpenseReimbursementEntity saveDetails(ExpenseReimbursementEntity expenseReimbursementEntity) {
		// TODO Auto-generated method stub
		return expenseReimbursementRepository.saveAndFlush(expenseReimbursementEntity);
	}

	@Override
	public ExpenseReimbursementEntity getExpenseDetails(Long id) {
		// TODO Auto-generated method stub
		return expenseReimbursementRepository.getById(id);
	}

	@Override
	public List<ExpenseReimbursementEntity> getExpenseReimbursementDetailsList(Long employeeID) {
		// TODO Auto-generated method stub
		return  expenseReimbursementRepository.findByEmployeeId(employeeID);
	}




	

}
