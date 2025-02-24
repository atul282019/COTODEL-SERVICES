package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;

public interface ErupiVoucherRequestService {
	
	public ErupiVoucherCreateRequest  saveErupiVoucherRequest(ErupiVoucherCreateRequest request);
	public List<ErupiVoucherCreationRequestEntity>  getErupiVoucherRequestEmployerId(Long employerId,Long employeeId);
	
}
