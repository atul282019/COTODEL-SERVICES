package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.ExpenseBandNumberEntity;

public interface ExpenseBandNumberDao {
	public ExpenseBandNumberEntity saveDetails(ExpenseBandNumberEntity expenseBandNumberEntity);
	public ExpenseBandNumberEntity findByEmployeeBandId(Long id);
	public ExpenseBandNumberEntity findByEmployerId(Long employerId);
	public void deleteDetails(Long id);
}
