package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryListDto;

public interface ErupiVoucherInitiateDetailsService {
	
	public ErupiVoucherCreateDetailsRequest  saveErupiVoucherInitiateDetails(ErupiVoucherCreateDetailsRequest request);
	public ErupiVoucherRevokeDetailsRequest  erupiVoucherRevokeDetails(ErupiVoucherRevokeDetailsRequest request);
	public List<ErupiVoucherCreatedDto>  getErupiVoucherCreateDetailsList(ErupiVoucherCreatedRequest request);
	public ErupiVoucherSummaryListDto  getErupiVoucherSummaryList(ErupiVoucherCreatedRequest request);
}
