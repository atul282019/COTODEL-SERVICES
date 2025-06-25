package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementFileDto;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;

public interface ExpenseReimbursementDao {
	public ExpenseReimbursementEntity saveDetails(ExpenseReimbursementEntity expenseReimbursementEntity);
	public ExpenseReimbursementEntity getExpenseDetails(Long id);
	public List<ExpenseReimbursementFileDto> getExpenseReimbursementDetailsList(Long employeeId);
	public void deleteDetails(Long id);
	
	public List<ExpenseReimbursementFileDto> getExpenseReimListByEmpId(Long employeeId);
	public List<ExpenseReimbursementFileDto> getExpenseReimListByEmplrId(Long employerId);
	public ExpenseReimbursementDto getExpenseReimById(Long Id);
}
