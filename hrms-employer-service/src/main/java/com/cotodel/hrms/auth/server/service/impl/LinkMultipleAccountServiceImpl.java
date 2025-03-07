package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.LinkSubMultipleAccountDao;
import com.cotodel.hrms.auth.server.dao.TransactionHistoryDao;
import com.cotodel.hrms.auth.server.dto.LinkMultipleAccountRequest;
import com.cotodel.hrms.auth.server.dto.LinkMultipleAccountUpdate;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleEntity;
import com.cotodel.hrms.auth.server.model.TransactionHistoryEntity;
import com.cotodel.hrms.auth.server.service.LinkMultipleAccountService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class LinkMultipleAccountServiceImpl implements LinkMultipleAccountService{


	private static final Logger logger = LoggerFactory.getLogger(LinkMultipleAccountServiceImpl.class);
	@Autowired
	LinkSubMultipleAccountDao  linkSubMultipleAccountDao;	
	
	@Autowired
	TransactionHistoryDao  transactionHistoryDao;
	

	@Override
	public LinkMultipleAccountRequest saveMultipleAccountRequest(LinkMultipleAccountRequest request) {
		
		LinkSubAccountMultipleEntity linkSubAccountMultipleEntity=null;
		String response=MessageConstant.RESPONSE_FAILED;
		try {
			ErupiLinkAccountEntity erupiLinkAccountEntity=new ErupiLinkAccountEntity();
			linkSubAccountMultipleEntity=new LinkSubAccountMultipleEntity();
			CopyUtility.copyProperties(request,linkSubAccountMultipleEntity);
			linkSubAccountMultipleEntity.setCreationDate(LocalDateTime.now());
			erupiLinkAccountEntity.setId(request.getLinkId());
			linkSubAccountMultipleEntity.setStatus(0l);
			linkSubAccountMultipleEntity.setStatusMessage("Requested");
			linkSubAccountMultipleEntity.setBalance(request.getAmountLimit());
			linkSubAccountMultipleEntity.setErupiLinkAccountEntity(erupiLinkAccountEntity);
			linkSubAccountMultipleEntity=linkSubMultipleAccountDao.saveDetails(linkSubAccountMultipleEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
			
		}catch (DataIntegrityViolationException ex) {
            // Handle the specific exception here
           // throw new CustomVoucherException("Voucher creation failed: " + ex.getMessage(), ex);
        request.setResponse(MessageConstant.DUP_ACC);
	}
		catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public LinkMultipleAccountUpdate saveMultipleAccountOrUpdateDr(LinkMultipleAccountUpdate request) {
		request.setResponse(MessageConstant.RESPONSE_FAILED);
		
		LinkSubAccountMultipleEntity link=linkSubMultipleAccountDao.getDetails(request.getId());
		if(link!=null) {
			Float balance=link.getBalance();
			Float amount=request.getAmount();
			if(amount>balance) {
				request.setResponse(MessageConstant.INSUFBAL);
				return request;				
			}
			balance=balance-amount;
			link.setBalance(balance);
			linkSubMultipleAccountDao.saveDetails(link);
			//linkSubMultipleAccountDao.updateBalance(balance, request.getId());
			TransactionHistoryEntity transactionHistoryEntity=new TransactionHistoryEntity();
			transactionHistoryEntity.setAmountDr(amount);
			transactionHistoryEntity.setLinkSubId(request.getId());
			transactionHistoryEntity.setOrgId(request.getOrgId());
			transactionHistoryEntity.setMerchantId(request.getMerchantId());
			transactionHistoryEntity.setAcNumber(request.getAcNumber());
			transactionHistoryEntity.setMobile(request.getMobile());
			transactionHistoryDao.saveDetails(transactionHistoryEntity);
			request.setResponse(MessageConstant.RESPONSE_SUCCESS);
		}
		
		return request;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public LinkMultipleAccountUpdate saveMultipleAccountOrUpdateCr(LinkMultipleAccountUpdate request) {
		request.setResponse(MessageConstant.RESPONSE_FAILED);
		
		LinkSubAccountMultipleEntity link=linkSubMultipleAccountDao.getDetails(request.getId());
		if(link!=null) {
			Float balance=link.getBalance();
			Float amount=request.getAmount();			
			balance=balance+amount;
			link.setBalance(balance);
			linkSubMultipleAccountDao.saveDetails(link);
			//linkSubMultipleAccountDao.updateBalance(balance, request.getId());
			TransactionHistoryEntity transactionHistoryEntity=new TransactionHistoryEntity();
			transactionHistoryEntity.setAmountCR(amount);
			transactionHistoryEntity.setLinkSubId(request.getId());
			transactionHistoryEntity.setOrgId(request.getOrgId());
			transactionHistoryEntity.setMerchantId(request.getMerchantId());
			transactionHistoryEntity.setAcNumber(request.getAcNumber());
			transactionHistoryEntity.setMobile(request.getMobile());
			transactionHistoryDao.saveDetails(transactionHistoryEntity);
			request.setResponse(MessageConstant.RESPONSE_SUCCESS);
		}
		
		return request;
	}

	@Override
	public List<LinkMultipleAccountRequest> getMultipleAccountList(LinkMultipleAccountRequest request) {
		List<LinkMultipleAccountRequest> liRequests=new ArrayList<>();
		List<LinkSubAccountMultipleEntity> list=linkSubMultipleAccountDao.getLinkMultipleDetails();	
		for (LinkSubAccountMultipleEntity linkSubAccountMultipleEntity : list) {
			LinkMultipleAccountRequest linkMultipleAccountRequest=new LinkMultipleAccountRequest();
			CopyUtility.copyProperties(linkSubAccountMultipleEntity,linkMultipleAccountRequest);
			linkMultipleAccountRequest.setLinkId(linkSubAccountMultipleEntity.getErupiLinkAccountEntity().getId());
			liRequests.add(linkMultipleAccountRequest);
		}
		return liRequests;
	}

	@Override
	public LinkMultipleAccountRequest updateMultipleAccount(LinkMultipleAccountRequest request) {
request.setResponse(MessageConstant.RESPONSE_FAILED);
		LinkSubAccountMultipleEntity linkSubAccountMultipleEntity=new LinkSubAccountMultipleEntity();
		LinkSubAccountMultipleEntity link=linkSubMultipleAccountDao.getDetails(request.getId());
		if(link!=null) {			
			if(request.getApprovedby()!=null && !request.getApprovedby().equalsIgnoreCase("")) {
				link.setApprovedby(request.getApprovedby());
				link.setStatus(1l);
				link.setStatusMessage("Approved");
				link.setApprovedDate(LocalDateTime.now());
				linkSubAccountMultipleEntity=linkSubMultipleAccountDao.saveDetails(link);			
				request.setResponse(MessageConstant.RESPONSE_SUCCESS);
			}else if(request.getRejectedby()!=null) {
				link.setRejectedby(request.getRejectedby());
				link.setStatus(2l);
				link.setStatusMessage("Rejected");
				link.setRejectedDate(LocalDateTime.now());
				linkSubAccountMultipleEntity=linkSubMultipleAccountDao.saveDetails(link);			
				request.setResponse(MessageConstant.RESPONSE_SUCCESS);
			}
		}
		
		return request;
	}
	
	

}
