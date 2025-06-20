package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;
import com.cotodel.hrms.auth.server.model.master.MccMasterEntity;

public interface ErupiVoucherRequestDao {
	public ErupiVoucherCreationRequestEntity saveDetails(ErupiVoucherCreationRequestEntity erCreationRequestEntity);
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequest(Long employerId);
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequestEmp(Long employeeId);
	public ErupiVoucherCreationRequestEntity getVoucherCreationRequestEmpById(Long id);
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequestApproved(Long employerId);
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequestApprovedEmp(Long employeeId);
	public byte[] getVoucherCreationRequestPurposeCode(String  purposeCode);
}
