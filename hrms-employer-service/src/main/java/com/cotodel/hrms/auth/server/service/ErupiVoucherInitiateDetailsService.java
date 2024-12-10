package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherRevokeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateListRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateOldDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRedemeRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeDetailsSingleRequest;

public interface ErupiVoucherInitiateDetailsService {
	
	public ErupiVoucherCreateDetailsRequest  saveErupiVoucherInitiateDetails(ErupiVoucherCreateDetailsRequest request);
	public ErupiVoucherRevokeDetailsRequest  erupiVoucherRevokeDetails(ErupiVoucherRevokeDetailsRequest request);
	public List<ErupiVoucherCreatedDto>  getErupiVoucherCreateDetailsList(ErupiVoucherCreatedRequest request);
	public List<ErupiVoucherSummaryDto>  getErupiVoucherSummaryList(ErupiVoucherCreatedRequest request);
	public ErupiVoucherRevokeDetailsSingleRequest  erupiVoucherRevokeSingleDetails(ErupiVoucherRevokeDetailsSingleRequest request);
	public String  erupiVoucherRedemDetails(ErupiVoucherRedemeRequest request);
	public List<ErupiVoucherCreateSummaryDto>  getErupiVoucherCreateSummaryList(ErupiVoucherCreatedRequest request);
	public ErupiVoucherCreateListRequest  saveErupiVoucherCreateListDetails(ErupiVoucherCreateListRequest request);
	public List<ErupiVoucherCreateOldDto>  getErupiVoucherCreateOldList(ErupiVoucherCreatedRequest request);
	
}
