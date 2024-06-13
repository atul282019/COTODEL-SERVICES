package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementRequest;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;

public interface ExpenseReimbursementService {
	
	public ExpenseReimbursementRequest  saveExpenseReimbursementFileUpload(ExpenseReimbursementRequest request);	
	public ExpenseReimbursementEntity  getExpenseReimbursementFileDownload(Long id);
}
