package com.cotodel.hrms.auth.server.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherInitiateDetailsDao;
import com.cotodel.hrms.auth.server.dto.AccountWiseAmountQueryDTO;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.PurposeCodeAmountDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemTransactionDto;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationDetailsEntity;
import com.cotodel.hrms.auth.server.repository.ErupiVoucherInitiateDetailsRepository;
@Repository
public class ErupiVoucherInitiateDetailsDaoImpl implements ErupiVoucherInitiateDetailsDao{

	@Autowired
	ErupiVoucherInitiateDetailsRepository erupiVoucherInitiateDetailsRepository;

	@Override
	public ErupiVoucherCreationDetailsEntity saveDetails(ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.saveAndFlush(erupiVoucherInitiateDetailsEntity);
	}

	@Override
	public int updateWorkflowId(Long id, Long WorkflowId) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.updateWorkflowId(id, WorkflowId);
	}

	@Override
	public List<ErupiVoucherCreatedDto> getVoucherCreationList(Long orgId,LocalDate startDate,LocalDate endDate) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.findVoucherCreateList(orgId,startDate,endDate);
	}

	@Override
	public ErupiVoucherCreationDetailsEntity getErupiVoucherCreationDetails(Long id) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.getById(id);
	}

	@Override
	public List<Object[]> getVoucherSummary( Long orgID) {
		// TODO Auto-generated method stub
		List<Object[]> result=erupiVoucherInitiateDetailsRepository.getVoucherSummary(orgID);
		return result;
	}

	@Override
	public List<Object[]> getVoucherCreateSummary(Long orgID) {
		// TODO Auto-generated method stub
		List<Object[]> result=erupiVoucherInitiateDetailsRepository.getVoucherCreateSummary(orgID);
		return result;
	}

	@Override
	public ErupiVoucherCreationDetailsEntity getCreationDetailsByTransactionId(String transactionid) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.findByTransactionId(transactionid);
	}

	@Override
	public List<Object[]> getVoucherCreateNameList(Long orgid) {
		// TODO Auto-generated method stub
		//return erupiVoucherInitiateDetailsRepository.findByNameByOrgId(orgid);
		List<Object[]> result=erupiVoucherInitiateDetailsRepository.findByNameByOrgId(orgid);
		return result;
	}

	@Override
	public List<Object[]> getVoucherCreateStatus(Long orgID) {
		// TODO Auto-generated method stub
		List<Object[]> result=erupiVoucherInitiateDetailsRepository.getVoucherCreateStatus(orgID);
		return result;
	}

	@Override
	public List<Object[]> getVoucherCreateBankNameList(Long orgid) {
		// TODO Auto-generated method stub
		List<Object[]> result=erupiVoucherInitiateDetailsRepository.getVoucherCreateBankList(orgid);
		return result;
	}

	@Override
	public List<Object[]> getVoucherCreateSummaryWithAccNo(Long orgid,String accNumber) {
		// TODO Auto-generated method stub
		List<Object[]> result=erupiVoucherInitiateDetailsRepository.getVoucherCreateSummaryWithAccNo(orgid, accNumber);
		return result;
	}

	@Override
	public ErupiVoucherCreatedDto getVoucherCreationById(Long id) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.findVoucherCreateById(id);
	}

	@Override
	public List<ErupiVoucherCreatedDto> getVoucherCreationListById(Long orgID, Long id) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.findVoucherCreateListById(orgID, id);
	}

	@Override
	public List<ErupiVoucherCreatedDateWiseDto> getVoucherCreationListDateWise(LocalDate startDate, LocalDate endDate,String bankCode) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.findVoucherCreateListDateWise(startDate, endDate,bankCode);
	}

	@Override
	public List<AccountWiseAmountQueryDTO> getVoucherAmountListAccountWise(Long orgId) {
		// TODO Auto-generated method stub
		//return erupiVoucherInitiateDetailsRepository.findTotalAmountGroupedByAccountAndOrg(orgId);
		List<Object[]> rawResults = erupiVoucherInitiateDetailsRepository.findTotalAmountGroupedByAccountAndOrg(orgId);

	    List<AccountWiseAmountQueryDTO> dtos = new ArrayList<AccountWiseAmountQueryDTO>();

	    for (Object[] row : rawResults) {
	        String accountNumber = (String) row[0];
	        Long orgIdValue = ((Number) row[1]).longValue();
	        double totalAmount = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;
	        double redeem = row[3] != null ? ((Number) row[3]).doubleValue() : 0.0;

	        dtos.add(new AccountWiseAmountQueryDTO(accountNumber, orgIdValue, totalAmount, redeem));
	    }
	    return dtos;
	}
	@Override
	public List<PurposeCodeAmountDto> getVoucherCreationListByPurposeCode(Long orgID, LocalDate startDate,
			LocalDate endDate) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.getTotalAmountByPurposeCode(startDate, endDate, orgID);
	}

	@Override
	public List<ErupiVoucherCreatedRedeemDto> getVoucherCreationListLimit(Long orgId, LocalDate startDate,
			LocalDate endDate) {
		List<ErupiVoucherCreatedRedeemDto> results= erupiVoucherInitiateDetailsRepository.findVoucherCreateListLimit(orgId, startDate, endDate);
		
		return results;

	    }

	@Override
	public List<ErupiVoucherCreatedDto> getVoucherCreationTransactionList(Long orgId) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.findVoucherCreateTransactionList(orgId);
	}

	@Override
	public List<ErupiVoucherCreatedRedeemTransactionDto> getVoucherCreationListRedeem(Long orgID, LocalDate startDate,
			LocalDate endDate) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.findVoucherCreateListRedeem(orgID, startDate, endDate);
	}

	@Override
	public List<Object[]> getVoucherCreateStatusView(Long orgId) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.getVoucherCreateStatusView(orgId);
	}

	@Override
	public List<Object[]> getVoucherCreateSummaryWithAccNoView(Long orgid, String accNumber) {
		// TODO Auto-generated method stub
		return erupiVoucherInitiateDetailsRepository.getVoucherCreateStatusAccountView(orgid, accNumber);
	}
	

	
	
	
	
}
