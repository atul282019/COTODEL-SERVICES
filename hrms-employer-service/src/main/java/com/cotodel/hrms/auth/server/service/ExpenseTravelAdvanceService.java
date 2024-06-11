package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceRequest;
import com.cotodel.hrms.auth.server.model.ExpanceTravelAdvanceEntity;

public interface ExpenseTravelAdvanceService {
	
	public ExpenseTravelAdvanceRequest  saveExpenseTravelAdvenceDetails(ExpenseTravelAdvanceRequest request);	
	public List<ExpanceTravelAdvanceEntity>  getExpenseTravelAdvenceDetailsList(Long employerid);
}
