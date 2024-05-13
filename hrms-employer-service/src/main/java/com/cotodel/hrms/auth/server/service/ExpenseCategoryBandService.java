package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandRequest;
import com.cotodel.hrms.auth.server.model.ExpenseCategoryBandEntity;

public interface ExpenseCategoryBandService {
	
	public ExpenseCategoryBandRequest  saveExpenseCategoryBandDetails(ExpenseCategoryBandRequest request);
	public ExpenseCategoryBandEntity  getCompEmployeeBandDetails(String bandid);
}
