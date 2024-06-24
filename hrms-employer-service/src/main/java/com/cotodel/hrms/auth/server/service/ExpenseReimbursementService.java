package com.cotodel.hrms.auth.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementRequest;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;
@Service
@Transactional
public interface ExpenseReimbursementService {
	
	public ExpenseReimbursementEntity  saveExpenseReimbursementFileUpload(ExpenseReimbursementRequest request);	
	public ExpenseReimbursementEntity  getExpenseReimbursementFileDownload(Long id);
}
