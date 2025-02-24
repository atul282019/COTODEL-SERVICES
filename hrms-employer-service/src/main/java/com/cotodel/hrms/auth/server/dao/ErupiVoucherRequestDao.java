package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;

public interface ErupiVoucherRequestDao {
	public ErupiVoucherCreationRequestEntity saveDetails(ErupiVoucherCreationRequestEntity erCreationRequestEntity);
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequest(Long employerId);
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequestEmp(Long employeeId);
	
}
