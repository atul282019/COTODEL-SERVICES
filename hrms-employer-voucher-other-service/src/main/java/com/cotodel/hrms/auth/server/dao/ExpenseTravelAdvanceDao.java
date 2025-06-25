package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.AdvanceRequestSettingEntity;

public interface ExpenseTravelAdvanceDao {
	public AdvanceRequestSettingEntity saveDetails(AdvanceRequestSettingEntity expenseCategoryBandEntity);
	public AdvanceRequestSettingEntity findByEmployerId(Long employerId);
}
