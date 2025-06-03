package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.AccountWiseAmountDTO;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherAmountRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherBankListDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherPurposeCodeRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTotalDetailDto;
import com.cotodel.hrms.auth.server.dto.PurposeCodeAmountDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateListRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateOldDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateTransactionRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRedemeRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherRevokeDetailsSingleRequest;

public interface ErupiVoucherInitiateDetailsService {
	
	public ErupiVoucherCreateDetailsRequest  saveErupiVoucherInitiateDetails(ErupiVoucherCreateDetailsRequest request);
	//public ErupiVoucherRevokeDetailsRequest  erupiVoucherRevokeDetails(ErupiVoucherRevokeDetailsRequest request);
	public List<ErupiVoucherCreatedDto>  getErupiVoucherCreateDetailsList(ErupiVoucherCreatedRequest request);
	public List<ErupiVoucherSummaryDto>  getErupiVoucherSummaryList(ErupiVoucherCreatedRequest request);
	public ErupiVoucherRevokeDetailsSingleRequest  erupiVoucherRevokeSingleDetails(ErupiVoucherRevokeDetailsSingleRequest request);
	public String  erupiVoucherRedemDetails(ErupiVoucherRedemeRequest request);
	public List<ErupiVoucherCreateSummaryDto>  getErupiVoucherCreateSummaryList(ErupiVoucherCreatedRequest request);
	public List<ErupiVoucherCreateDetailsRequest>  saveErupiVoucherCreateListDetails(ErupiVoucherCreateListRequest request);
	public List<ErupiVoucherCreateOldDto>  getErupiVoucherCreateOldList(ErupiVoucherCreatedRequest request);
	//public ErupiMultipleVoucherCreateRequest  saveErupiMultipleVoucherCreation(ErupiMultipleVoucherCreateRequest request);
	public ErupiVoucherCreateDetailsRequest  saveErupiVoucherInitiateDetailsNew(ErupiVoucherCreateDetailsRequest request);
	public ErupiVoucherTotalDetailDto  getErupiVoucherCreateStatus(ErupiVoucherCreatedRequest request);
	public List<ErupiVoucherBankListDto>  getErupiVoucherCreateBAnkList(ErupiVoucherCreatedRequest request);
	public ErupiVoucherTotalDetailDto  getErupiVoucherCreateDetailByAccount(ErupiVoucherCreatedRequest request);
	//public ErupiVoucherCreatedDto  getErupiVoucherCreateDetailsById(ErupiVoucherCreatedRequest request);
	public List<ErupiVoucherCreatedDateWiseDto>  getErupiVoucherCreateDetailsListDateWise(ErupiVoucherCreatedDateWiseRequest request);
	public boolean  getSecurityCheck(String clientId,String secretId,String bankCode);
	public ErupiVoucherCreateDetailsRequest  saveErupiVoucherInitiateDetailsNewBulk(ErupiVoucherCreateDetailsRequest request);
	public List<AccountWiseAmountDTO>  getErupiVoucherAmountDetailByAccount(ErupiVoucherAmountRequest request);
	public List<PurposeCodeAmountDto>  getErupiVoucherCreateDetailsListByPuposeCode(ErupiVoucherPurposeCodeRequest request);
	public List<ErupiVoucherCreatedRedeemDto>  getErupiVoucherCreateDetailsListLimit(ErupiVoucherCreatedRequest request);
	public List<ErupiVoucherCreatedDto>  getErupiVoucherCreateDetailsTransactionList(ErupiVoucherCreateTransactionRequest request);
}
