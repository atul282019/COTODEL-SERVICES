package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingListRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingNewRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

public interface EmployeeOnboardingService {
	
	public EmployeeOnboardingRequest  saveEmployeeDetails(EmployeeOnboardingRequest	 request);
	public EmployeeOnboardingNewRequest  saveEmployeeDetailsNew(EmployeeOnboardingNewRequest	 request);
	public EmployeeOnboardingRequest  saveBulkEmployeeDetails(EmployeeOnboardingRequest	 request);
	public List<EmployeeOnboardingEntity>  getEmployeeDetailsList(Long employerid);
	public EmployeeOnboardingListRequest  confirmBulkEmployeeDetails(EmployeeOnboardingListRequest	 request);
	public EmployeeOnboardingListRequest  tryBulkEmployeeDetails(EmployeeOnboardingListRequest	 request);
	public EmployeeOnboardingEntity  getEmployeeDetailsById(Long id);
	public List<EmployeeOnboardingEntity>  getEmployeeDetailsByManagerId(Long managerId);
}
