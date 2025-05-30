package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseReimbursementDao;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementFileDto;
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
	public List<ExpenseReimbursementFileDto> getExpenseReimbursementDetailsList(Long employeeID) {
		// TODO Auto-generated method stub
		return  expenseReimbursementRepository.findByEmployeeId(employeeID);
	}

	@Override
	public void deleteDetails(Long id) {
		expenseReimbursementRepository.deleteById(id);		
	}

	@Override
	public List<ExpenseReimbursementFileDto> getExpenseReimListByEmpId(Long employeeID) {
		
		List<ExpenseReimbursementFileDto> exList=expenseReimbursementRepository.findExpenseReimByEmpId(employeeID);
		return exList;
	}

	@Override
	public List<ExpenseReimbursementFileDto> getExpenseReimListByEmplrId(Long employerId) {
		
		List<ExpenseReimbursementFileDto> exList=expenseReimbursementRepository.findExpenseReimByEmplrId(employerId);
		return exList;
	}

	@Override
	public ExpenseReimbursementDto getExpenseReimById(Long Id) {
		// TODO Auto-generated method stub
		return expenseReimbursementRepository.findExpenseReimById(Id);
	}




	

}
