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

import com.cotodel.hrms.auth.server.dao.ErupiLinkAccountDao;
import com.cotodel.hrms.auth.server.dao.LinkSubMultipleAccountDao;
import com.cotodel.hrms.auth.server.dao.LinkSubMultipleAccountTempDao;
import com.cotodel.hrms.auth.server.dao.TransactionHistoryDao;
import com.cotodel.hrms.auth.server.dto.LinkMultipleAccountRequest;
import com.cotodel.hrms.auth.server.dto.LinkMultipleAccountUpdate;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleEntity;
import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleTempEntity;
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
	LinkSubMultipleAccountTempDao linkSubMultipleAccountTempDao;
	
	@Autowired
	TransactionHistoryDao  transactionHistoryDao;
	
	@Autowired
	ErupiLinkAccountDao  erupiLinkAccountDao;

	@Override
	public LinkMultipleAccountRequest saveMultipleAccountRequest(LinkMultipleAccountRequest request) {
		
		LinkSubAccountMultipleTempEntity linkSubAccountMultipleTempEntity=null;
		String response=MessageConstant.RESPONSE_FAILED;
		try {
			Float amount=request.getAmountLimit();
			if(amount<=0) {
				response=MessageConstant.BAL;
				request.setResponse(response);
				return request;
			}
			ErupiLinkAccountEntity erupiLinkAccountEntity=new ErupiLinkAccountEntity();
			linkSubAccountMultipleTempEntity=new LinkSubAccountMultipleTempEntity();
			
			CopyUtility.copyProperties(request,linkSubAccountMultipleTempEntity);
			linkSubAccountMultipleTempEntity.setCreationDate(LocalDateTime.now());
			erupiLinkAccountEntity.setId(request.getLinkId());
			linkSubAccountMultipleTempEntity.setStatus(0l);
			linkSubAccountMultipleTempEntity.setStatusMessage("Requested");
			linkSubAccountMultipleTempEntity.setBalance(request.getAmountLimit());
			//linkSubAccountMultipleTempEntity.setErupiLinkAccountEntity(erupiLinkAccountEntity);
			linkSubAccountMultipleTempEntity=linkSubMultipleAccountTempDao.saveDetails(linkSubAccountMultipleTempEntity);
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
			transactionHistoryEntity.setCreationDate(LocalDateTime.now());
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
			transactionHistoryEntity.setCreationDate(LocalDateTime.now());
			transactionHistoryDao.saveDetails(transactionHistoryEntity);
			request.setResponse(MessageConstant.RESPONSE_SUCCESS);
		}
		
		return request;
	}

	@Override
	public List<LinkMultipleAccountRequest> getMultipleAccountList(LinkMultipleAccountRequest request) {
		List<LinkMultipleAccountRequest> liRequests=new ArrayList<>();
		//List<LinkSubAccountMultipleEntity> mainAccount=linkSubMultipleAccountDao.getLinkMultipleDetailsByOrgId(request.getOrgId());
		Float balance=0f;
//		if(mainAccount.size()>0) {
//		LinkSubAccountMultipleEntity linkSubAccountMultipleEntity=mainAccount.get(0);
//		balance=linkSubAccountMultipleEntity.getBalance();
//		}
		
		List<LinkSubAccountMultipleTempEntity> list=linkSubMultipleAccountTempDao.getLinkMultipleDetailsList();	
		for (LinkSubAccountMultipleTempEntity linkSubAccountMultipleTempEntity : list) {
			LinkMultipleAccountRequest linkMultipleAccountRequest=new LinkMultipleAccountRequest();
			CopyUtility.copyProperties(linkSubAccountMultipleTempEntity,linkMultipleAccountRequest);
			LinkSubAccountMultipleEntity linkSubAccountMultipleEntity =linkSubMultipleAccountDao.getLinkMultipleDetailsByOrgIdWithOne(linkMultipleAccountRequest.getOrgId());
			balance=0f;
			if(linkSubAccountMultipleEntity!=null) {
				balance=linkSubAccountMultipleEntity.getBalance();
			}
			//linkMultipleAccountRequest.setLinkId(linkSubAccountMultipleTempEntity.getErupiLinkAccountEntity().getId());
			linkMultipleAccountRequest.setBalance(balance);
			liRequests.add(linkMultipleAccountRequest);
		}
		return liRequests;
	}

	@Override
	@Transactional
	public LinkMultipleAccountRequest updateMultipleAccount(LinkMultipleAccountRequest request) {
		request.setResponse(MessageConstant.RESPONSE_FAILED);
		LinkSubAccountMultipleTempEntity linkSubAccountMultipleEntity=new LinkSubAccountMultipleTempEntity();
		LinkSubAccountMultipleEntity linkSubAccountMultipleEntity1=new LinkSubAccountMultipleEntity();
		LinkSubAccountMultipleEntity linkSubAccountEntity=null;
		ErupiLinkAccountEntity erupiLinkAccountEntity=null;
		//erupiLinkAccountDao
		//ErupiLinkAccountEntity findByErupiLinkAccNumber(String accNo);
		
		try {			
		
			LinkSubAccountMultipleTempEntity link=linkSubMultipleAccountTempDao.getDetails(request.getId());
		if(link!=null && link.getStatus()==0) {			
			if(request.getApprovedby()!=null && !request.getApprovedby().equalsIgnoreCase("")) {
				erupiLinkAccountEntity=erupiLinkAccountDao.findByErupiLinkAccNumber(link.getAcNumber());
				linkSubAccountEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(link.getAcNumber(), link.getOrgId());
				
				if(linkSubAccountEntity!=null) {
					
					Float amtLmt=linkSubAccountEntity.getAmountLimit()+request.getAmountLimit();
					Float bal=linkSubAccountEntity.getBalance()+request.getAmountLimit();
					linkSubAccountEntity.setAmountLimit(amtLmt);
					linkSubAccountEntity.setBalance(bal);
					linkSubMultipleAccountDao.saveDetails(linkSubAccountEntity);
					Float bala=erupiLinkAccountEntity.getAccountBalance()==null?0:erupiLinkAccountEntity.getAccountBalance();
					Float accountBalance=bala+request.getAmountLimit();
					erupiLinkAccountEntity.setAccountBalance(accountBalance);
					erupiLinkAccountDao.saveDetails(erupiLinkAccountEntity);
					
					
				}else {
					//ErupiLinkAccountEntity erupiLinkAccountEntity=new ErupiLinkAccountEntity();
					CopyUtility.copyProperties(request,linkSubAccountMultipleEntity1);
					linkSubAccountMultipleEntity1.setCreationDate(LocalDateTime.now());
					//erupiLinkAccountEntity.setId(request.getLinkId());
					linkSubAccountMultipleEntity1.setStatus(1l);
					linkSubAccountMultipleEntity1.setStatusMessage("Approved");
					linkSubAccountMultipleEntity1.setBalance(request.getAmountLimit());
					//linkSubAccountMultipleEntity1.setErupiLinkAccountEntity(erupiLinkAccountEntity);
					linkSubAccountMultipleEntity1=linkSubMultipleAccountDao.saveDetails(linkSubAccountMultipleEntity1);
					Float bala=erupiLinkAccountEntity.getAccountBalance()==null?0:erupiLinkAccountEntity.getAccountBalance();
					Float accountBalance=bala+request.getAmountLimit();
					erupiLinkAccountEntity.setAccountBalance(accountBalance);
					erupiLinkAccountDao.saveDetails(erupiLinkAccountEntity);
					//response=MessageConstant.RESPONSE_SUCCESS;
				}
				link.setApprovedby(request.getApprovedby());
				link.setStatus(1l);
				link.setStatusMessage("Approved");
				link.setApprovedDate(LocalDateTime.now());
				linkSubAccountMultipleEntity=linkSubMultipleAccountTempDao.saveDetails(link);			
				request.setResponse(MessageConstant.RESPONSE_SUCCESS);
				
			}else if(request.getRejectedby()!=null) {
				link.setRejectedby(request.getRejectedby());
				link.setStatus(2l);
				link.setStatusMessage("Rejected");
				link.setRejectedDate(LocalDateTime.now());
				linkSubAccountMultipleEntity=linkSubMultipleAccountTempDao.saveDetails(link);			
				request.setResponse(MessageConstant.RESPONSE_SUCCESS);
			}
		}else if(link!=null && link.getStatus()==1){
			request.setResponse("Request already Approved");
		}else if(link!=null && link.getStatus()==2){
			request.setResponse("Request already Rejected");
		}
		}catch (DataIntegrityViolationException ex) {
            // Handle the specific exception here
        request.setResponse(MessageConstant.DUP_ACC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	@Override
	public String getMultipleAccountBalance(LinkMultipleAccountRequest request) {
		// TODO Auto-generated method stub
		LinkSubAccountMultipleEntity linkSubAccountMultipleEntity=null;
		String balance="";
		Float amount=0f;
		try {
			linkSubAccountMultipleEntity=linkSubMultipleAccountDao.getLinkMultipleAccountByAccNoOrgId(request.getAcNumber(), request.getOrgId());
			if(linkSubAccountMultipleEntity!=null) {
			amount=linkSubAccountMultipleEntity.getBalance();
			}else {
;				balance="0";
			}
			balance=amount==null?"0":amount.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return balance;
	}
	

}
