package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.ExpanceTravelAdvanceEntity;

public interface ExpenseTravelAdvanceDao {
	public ExpanceTravelAdvanceEntity saveDetails(ExpanceTravelAdvanceEntity expenseCategoryBandEntity);
	public ExpanceTravelAdvanceEntity findByEmployerId(Long employerId);
}
