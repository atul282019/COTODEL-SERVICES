package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.AdvanceTravelRequest;
import com.cotodel.hrms.auth.server.dto.ExpanceTravelAdvance;
import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceRequest;
import com.cotodel.hrms.auth.server.model.AdvanceRequestSettingEntity;
import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;

public interface ExpenseTravelAdvanceService {
	
	public ExpenseTravelAdvanceRequest  saveExpenseTravelAdvenceDetails(ExpenseTravelAdvanceRequest request);	
	public AdvanceRequestSettingEntity  getExpenseTravelAdvenceDetails(Long employerid);
	public ExpanceTravelAdvance  getExpenseTravelAdvence(Long employerid);
	
	public AdvanceTravelRequest  saveAdvenceTravelRequestDetails(AdvanceTravelRequest request);
	public List<AdvanceTravelRequestEntity>  getAdvenceTravelListByEmployerId(Long employerid,Long employeeId);
	public AdvanceTravelRequestEntity  getAdvenceTravelListById(Long id);
}
