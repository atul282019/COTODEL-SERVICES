package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingManagerRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;

public interface ErupiVoucherRequestService {
	
	public ErupiVoucherCreateRequest  saveErupiVoucherRequest(ErupiVoucherCreateRequest request);
	public List<ErupiVoucherCreationRequestEntity>  getErupiVoucherRequestEmployerId(Long employerId,Long employeeId);
	public List<ErupiVoucherCreationRequestEntity>  getErupiVoucherRequestApprovedEmployerId(Long employerId,Long employeeId);
	public List<ErupiVoucherCreationRequestEntity>  getErupiVoucherRequestByMgrId(Long mgrId);
	public EmployeeOnboardingManagerRequest  updateErupiVoucherRequestUpdate(EmployeeOnboardingManagerRequest request);
	
}
