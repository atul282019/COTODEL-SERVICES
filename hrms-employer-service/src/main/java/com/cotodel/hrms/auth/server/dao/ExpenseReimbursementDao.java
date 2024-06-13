package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;

public interface ExpenseReimbursementDao {
	public ExpenseReimbursementEntity saveDetails(ExpenseReimbursementEntity expenseReimbursementEntity);
	public ExpenseReimbursementEntity getExpenseDetails(Long id);
	
}
