package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.AdvanceTravelAllRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelAllUpdateRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelCashRequest;
import com.cotodel.hrms.auth.server.dto.AdvanceTravelRequest;
import com.cotodel.hrms.auth.server.dto.ApprovalTravelReimbursement;
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
	public AdvanceTravelAllRequest  getAdvenceTravelListByStatus(Long employerid,Long employeeId,int status);
	public AdvanceTravelCashRequest  saveAdvenceTravelRequestCashDetails(AdvanceTravelCashRequest request);	
	public AdvanceTravelAllUpdateRequest updateAdvenceTravelList(AdvanceTravelAllUpdateRequest advanceTravelAllUpdateRequest);
	public String  deleteAdvenceTravelById(Long id);
	public List<AdvanceTravelRequestEntity>  getAdvenceTravelApprovalEmployerId(Long employerid);
	public ApprovalTravelReimbursement ApprovalAdvenceTravel(ApprovalTravelReimbursement approvalTravelReimbursement);
	public List<AdvanceTravelRequestEntity>  advenceTravelListById(Long id);
	
}
