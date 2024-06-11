package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ExpanceTravelAdvanceEntity;

public interface ExpenseTravelAdvanceDao {
	public ExpanceTravelAdvanceEntity saveDetails(ExpanceTravelAdvanceEntity expenseCategoryBandEntity);
	public List<ExpanceTravelAdvanceEntity> findByEmployerId(Long employerId);
}
