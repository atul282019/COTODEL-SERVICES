package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.ExpenseCategoryBandEntity;

public interface ExpenseCategoryBandDao {
	public ExpenseCategoryBandEntity saveDetails(ExpenseCategoryBandEntity expenseCategoryBandEntity);
	public ExpenseCategoryBandEntity getEmployeeBandDetails(String bandId);
}
