package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountRequest;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountWithOutResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;

public interface ErupiLinkAccountService {
	
	public ErupiLinkAccountRequest  saveErupiAccountDetails(ErupiLinkAccountRequest request);
	public ErupiLinkAccountWithOutResponse  getErupiAccountDetails(ErupiLinkAccountRequest request);
	public List<ErupiLinkAccountWithOutResponse>  getErupiAccountListDetails(ErupiLinkAccountRequest request);
	public ErupiLinkAccountEntity  getErupiAccountDetails(String accNumber);	
	public ErupiLinkAccountRequest  updateErupiAccountPSFlag(ErupiLinkAccountRequest request);
	public ErupiLinkAccountRequest  getErupiPrimaryAccountDetails(Long orgId);
	public ErupiLinkAccountRequest  updateErupiAccountDisable(ErupiLinkAccountRequest request);
	public ErupiLinkAccountRequest  updateErupiAccountEnable(ErupiLinkAccountRequest request);
	public List<ErupiLinkAccountWithOutResponse>  getErupiAccountListDetailsWithStatus(ErupiVoucherCreatedRequest request);
}
