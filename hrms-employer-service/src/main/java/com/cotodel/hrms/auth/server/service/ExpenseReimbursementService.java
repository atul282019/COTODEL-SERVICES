package com.cotodel.hrms.auth.server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementFileDto;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementRequest;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;
@Service
@Transactional
public interface ExpenseReimbursementService {
	
	public ExpenseReimbursementEntity  saveExpenseReimbursementFileUpload(ExpenseReimbursementRequest request);	
	public ExpenseReimbursementEntity  getExpenseReimbursementFileDownload(Long id);
	public List<ExpenseReimbursementFileDto>  getExpenseReimbFileByEmpID(Long employeeId);
	public ExpenseReimbursementRequest  getExpenseReimbursementFileDelete(ExpenseReimbursementRequest request);
	public ExpenseReimbursementEntity  saveExpenseReimbursementFileUploadSubmit(ExpenseReimbursementRequest request);
	public List<ExpenseReimbursementFileDto>  getExpenseReimbFileByEmpAndEmprId(Long employerId,Long employeeId);
	public ExpenseReimbursementDto  getExpenseReimbFileById(Long id);
	public ExpenseReimbursementEntity  updateExpenseReimbursementApprover(ExpenseReimbursementRequest request);
}
