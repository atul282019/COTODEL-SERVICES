package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ExpenseCategoryBandEntity;

public interface ExpenseCategoryBandDao {
	public ExpenseCategoryBandEntity saveDetails(ExpenseCategoryBandEntity expenseCategoryBandEntity);
	public ExpenseCategoryBandEntity getEmployeeBandDetails(String bandId);
	public ExpenseCategoryBandEntity findByEmployeeBandId();
	public ExpenseCategoryBandEntity findByEmployeeBandId(Long id);
	public List<ExpenseCategoryBandEntity> findByEmployerId(Long employerId);
	public ExpenseCategoryBandEntity findByEmployeeBandIdWithEmployer(Long id,Long employerId);
	public int updateDetails(ExpenseCategoryBandEntity expenseCategoryBandEntity);
	public void deleteDetails(Long id);
}
