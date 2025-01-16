package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;

public interface ExpenseReimbursementDao {
	public ExpenseReimbursementEntity saveDetails(ExpenseReimbursementEntity expenseReimbursementEntity);
	public ExpenseReimbursementEntity getExpenseDetails(Long id);
	public List<ExpenseReimbursementEntity> getExpenseReimbursementDetailsList(Long employeeId);
	public void deleteDetails(Long id);
	
	public List<ExpenseReimbursementDto> getExpenseReimListByEmpId(Long employeeId);
	public List<ExpenseReimbursementDto> getExpenseReimListByEmplrId(Long employerId);
}
