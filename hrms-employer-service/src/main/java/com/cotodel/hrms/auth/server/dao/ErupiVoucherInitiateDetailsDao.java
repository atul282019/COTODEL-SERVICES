package com.cotodel.hrms.auth.server.dao;

import java.time.LocalDate;
import java.util.List;

import com.cotodel.hrms.auth.server.dto.AccountWiseAmountDTO;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.PurposeCodeAmountDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemDto;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationDetailsEntity;

public interface ErupiVoucherInitiateDetailsDao {
	public ErupiVoucherCreationDetailsEntity saveDetails(ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity);	
	public int updateWorkflowId(Long id ,Long WorkflowId);		
	public List<ErupiVoucherCreatedDto> getVoucherCreationList(Long orgID,LocalDate startDate,LocalDate endDate);
	public ErupiVoucherCreationDetailsEntity getErupiVoucherCreationDetails(Long id);
	public List<Object []> getVoucherSummary(Long orgID);
	public List<Object []> getVoucherCreateSummary(Long orgID);
	public List<Object []> getVoucherCreateStatus(Long orgID);
	public ErupiVoucherCreationDetailsEntity getCreationDetailsByTransactionId(String transactionid);
	public List<Object[]> getVoucherCreateNameList(Long orgid);
	public List<Object[]> getVoucherCreateBankNameList(Long orgid);
	public List<Object[]> getVoucherCreateSummaryWithAccNo(Long orgid,String accNumber);
	public ErupiVoucherCreatedDto getVoucherCreationById(Long id);
	public List<ErupiVoucherCreatedDto> getVoucherCreationListById(Long orgID,Long id);
	public List<ErupiVoucherCreatedDateWiseDto> getVoucherCreationListDateWise(LocalDate startDate,LocalDate endDate,String bankCode);
	public List<AccountWiseAmountDTO> getVoucherAmountListAccountWise(Long orgId);
	public List<PurposeCodeAmountDto> getVoucherCreationListByPurposeCode(Long orgID,LocalDate startDate,LocalDate endDate);
	public List<ErupiVoucherCreatedRedeemDto> getVoucherCreationListLimit(Long orgID,LocalDate startDate,LocalDate endDate);
	public List<ErupiVoucherCreatedDto> getVoucherCreationTransactionList(Long orgId);
}
