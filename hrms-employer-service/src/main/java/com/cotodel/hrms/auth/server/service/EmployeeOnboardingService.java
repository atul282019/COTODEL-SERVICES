package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingDriverRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingDto;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingListActiveResponse;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingListRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingNewRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingReputeRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingReputeUpdateRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingUserListDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.UpdateEmployeeStatusRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

public interface EmployeeOnboardingService {
	
	public EmployeeOnboardingRequest  saveEmployeeDetails(EmployeeOnboardingRequest	 request);
	public EmployeeOnboardingNewRequest  saveEmployeeDetailsNew(EmployeeOnboardingNewRequest	 request);
	public EmployeeOnboardingRequest  saveBulkEmployeeDetails(EmployeeOnboardingRequest	 request);
	public List<EmployeeOnboardingEntity>  getEmployeeDetailsList(Long employerid);
	public EmployeeOnboardingListRequest  confirmBulkEmployeeDetails(EmployeeOnboardingListRequest	 request);
	//public EmployeeOnboardingListRequest  tryBulkEmployeeDetails(EmployeeOnboardingListRequest	 request);
	public EmployeeOnboardingEntity  getEmployeeDetailsById(Long id);
	public List<EmployeeOnboardingEntity>  getEmployeeDetailsByManagerId(Long managerId);
	public UpdateEmployeeStatusRequest  updateEmployeeStatus(UpdateEmployeeStatusRequest	 request);
	public EmployeeOnboardingEntity  getEmployeeDetailsByUserId(Long id);
	public EmployeeOnboardingNewRequest  updateEmployeeDetailsNew(EmployeeOnboardingNewRequest	 request);
	public EmployeeOnboardingRequest  saveEmployeeDetailsRepute(EmployeeOnboardingReputeRequest	 request);
	public EmployeeOnboardingRequest  updateEmployeeDetailsRepute(EmployeeOnboardingReputeUpdateRequest	 request);
	public List<EmployeeOnboardingDto>  getDriverEmployeeList(EmployeeOnboardingDriverRequest	 request);
	public EmployeeOnboardingListActiveResponse  getEmployeeDetailsListTotalActive(Long employerid,String type);
	public List<EmployeeOnboardingDto>  getDriverEmployeeAssignList(EmployeeOnboardingDriverRequest	 request);
	public List<EmployeeOnboardingUserListDto>  getEmployeeList(ErupiVoucherCreatedRequest	 request);
}
