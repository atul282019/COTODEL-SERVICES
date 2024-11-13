package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsRequest;

public interface ErupiVoucherInitiateDetailsService {
	
	public ErupiVoucherCreateDetailsRequest  saveErupiVoucherInitiateDetails(ErupiVoucherCreateDetailsRequest request);
	public ErupiVoucherRevokeDetailsRequest  erupiVoucherRevokeDetails(ErupiVoucherRevokeDetailsRequest request);
	public ErupiVoucherCreateDetailsRequest  getErupiVoucherCreateDetailsList(ErupiVoucherCreateDetailsRequest request);
}
