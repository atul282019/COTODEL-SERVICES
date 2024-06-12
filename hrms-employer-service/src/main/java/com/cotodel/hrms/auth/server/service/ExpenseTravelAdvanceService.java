package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.ExpanceTravelAdvance;
import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceRequest;
import com.cotodel.hrms.auth.server.model.ExpanceTravelAdvanceEntity;

public interface ExpenseTravelAdvanceService {
	
	public ExpenseTravelAdvanceRequest  saveExpenseTravelAdvenceDetails(ExpenseTravelAdvanceRequest request);	
	public ExpanceTravelAdvanceEntity  getExpenseTravelAdvenceDetails(Long employerid);
	public ExpanceTravelAdvance  getExpenseTravelAdvence(Long employerid);
}
