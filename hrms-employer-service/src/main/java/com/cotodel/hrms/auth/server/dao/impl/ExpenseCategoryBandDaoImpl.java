package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseCategoryBandDao;
import com.cotodel.hrms.auth.server.model.ExpenseCategoryBandEntity;
import com.cotodel.hrms.auth.server.repository.ExpenseCategoryBandRepository;
@Repository

public class ExpenseCategoryBandDaoImpl implements ExpenseCategoryBandDao{

	@Autowired
	ExpenseCategoryBandRepository expenseCategoryBandRepository;

	@Override
	@Modifying
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
	public ExpenseCategoryBandEntity findByEmployeeBandId(Long id) {
		// TODO Auto-generated method stub
		return expenseCategoryBandRepository.findByEmployeeId(id);
	}


	@Override
	public List<ExpenseCategoryBandEntity> findByEmployerId(Long employerId) {
		// TODO Auto-generated method stub
		return expenseCategoryBandRepository.findByExpenseEmpoyerId(employerId);
	}


	@Override
	public ExpenseCategoryBandEntity findByEmployeeBandIdWithEmployer(Long id, Long employerId) {
		// TODO Auto-generated method stub
		return expenseCategoryBandRepository.findByEmployeeExpenseCodeWithEmployer(id, employerId);
	}


	@Override
	public int updateDetails(ExpenseCategoryBandEntity expenseCategoryBandEntity) {
		// TODO Auto-generated method stub
		 return expenseCategoryBandRepository.updateAmountById(expenseCategoryBandEntity.getExpenseCategory(), expenseCategoryBandEntity.getExpenseCode(),expenseCategoryBandEntity.getDayToExpiry(),expenseCategoryBandEntity.getExpenseLimit(), expenseCategoryBandEntity.getId());
	}


	@Override
	public void deleteDetails(Long id) {
		// TODO Auto-generated method stub
		 expenseCategoryBandRepository.deleteById(id);
	}
	

}
