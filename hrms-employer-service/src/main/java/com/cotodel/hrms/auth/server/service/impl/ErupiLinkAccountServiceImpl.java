package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.auth.server.dao.BankMasterDao;
import com.cotodel.hrms.auth.server.dao.ErupiLinkAccountDao;
import com.cotodel.hrms.auth.server.dao.LinkSubMultipleAccountDao;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountRequest;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountWithOutResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedRequest;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.repository.EmployeeOnboardingRepository;
import com.cotodel.hrms.auth.server.repository.ErupiLinkAccountRepository;
import com.cotodel.hrms.auth.server.service.ErupiLinkAccountService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiLinkAccountServiceImpl implements ErupiLinkAccountService{

	@Autowired
	ErupiLinkAccountDao  erupiLinkAccountDao;
	
	@Autowired
	BankMasterDao  bankMasterDao;
	
	@Autowired
	EmployeeOnboardingRepository employeeOnboardingRepository;
	
	@Autowired
	ErupiLinkAccountRepository erupiLinkAccountRepository;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	LinkSubMultipleAccountDao  linkSubMultipleAccountDao;
	
	@Override
	public ErupiLinkAccountRequest saveErupiAccountDetails(ErupiLinkAccountRequest request) {
		String response="";
		String response1="";
		String tokenvalue="";
		ErupiLinkAccountEntity linkAccountErupiEntity=null;
		List<EmployeeOnboardingEntity> list=new ArrayList<>();
		try {
			
//			list=employeeOnboardingRepository.findByOnboardingList(request.getOrgId());
//			if(list==null || list.size()==0) {
//				response=MessageConstant.ORG_ONBOARDING;
//				request.setResponse(response);
//				return request;
//			}
			
			TokenGeneration token=new TokenGeneration();
			UserRequest userRequest=new UserRequest();
			userRequest.setId(request.getOrgId());
				tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
			 response1 = CommonUtility.userRequest(tokenvalue, MessageConstant.gson.toJson(userRequest),
					applicationConstantConfig.userServiceApiUrl+CommonUtils.existOrgid,applicationConstantConfig.apiSignaturePublicPath,applicationConstantConfig.apiSignaturePrivatePath);
			if (!ObjectUtils.isEmpty(response1)) {
				JSONObject demoRes = new JSONObject(response1);
				boolean status = demoRes.getBoolean("status");
				if (!status) {
					response=MessageConstant.ORG_ONBOARDING;
					request.setResponse(response);
					return request;
						
					}
			}else {
				response=MessageConstant.ORG_ONBOARDING;
				request.setResponse(response);
				return request;
			}
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			linkAccountErupiEntity=new ErupiLinkAccountEntity();
			CopyUtility.copyProperties(request,linkAccountErupiEntity);
			if(!linkAccountErupiEntity.getAcNumber().equalsIgnoreCase(linkAccountErupiEntity.getConirmAccNumber())) {				
				response=MessageConstant.ACC_MIS_MATCH;
				request.setResponse(response);
				return request;
			}
			LocalDateTime eventDate = LocalDateTime.now();	
			linkAccountErupiEntity.setCreationDate(eventDate);
			linkAccountErupiEntity.setPsFlag("Secondary");
			linkAccountErupiEntity=erupiLinkAccountDao.saveDetails(linkAccountErupiEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		}catch (DataIntegrityViolationException e) {
			response=MessageConstant.DUP_ACC;
			request.setResponse(response);
		}
		catch (Exception e) {
			log.error("Error in ErupiLinkAccountServiceImpl......."+e.getMessage());
		}
		return request;
	}

	@Override
	public ErupiLinkAccountWithOutResponse getErupiAccountDetails(ErupiLinkAccountRequest request) {
		ErupiLinkAccountEntity erupiLinkAccountEntity=null;
		String response="";
		ErupiLinkAccountWithOutResponse erupiLinkAccountWithOutResponse=new ErupiLinkAccountWithOutResponse();
		try {
			erupiLinkAccountEntity=erupiLinkAccountDao.findByOrgId(request.getOrgId());
			CopyUtility.copyProperties(erupiLinkAccountEntity,erupiLinkAccountWithOutResponse);
		} catch (Exception e) {
			
			response=MessageConstant.RESPONSE_FAILED;
			//e.printStackTrace();
			//request.setResponse(response);
		}
		return erupiLinkAccountWithOutResponse;
	}

	@Override
	public List<ErupiLinkAccountWithOutResponse> getErupiAccountListDetails(ErupiLinkAccountRequest request) {
		List<ErupiLinkAccountEntity> erupiLinkAccountEntity=null;
		String response="";
		ErupiLinkAccountWithOutResponse erupiLinkAccountWithOutResponse=null;
		List<ErupiLinkAccountWithOutResponse> erupiLinkList=new ArrayList<>();
		try {
			erupiLinkAccountEntity=erupiLinkAccountDao.findByErupiLinkOrgId(request.getOrgId());
			for (ErupiLinkAccountEntity erupiLinkAccountWithOutResponse2 : erupiLinkAccountEntity) {
				erupiLinkAccountWithOutResponse=new ErupiLinkAccountWithOutResponse();
				CopyUtility.copyProperties(erupiLinkAccountWithOutResponse2,erupiLinkAccountWithOutResponse);
				erupiLinkList.add(erupiLinkAccountWithOutResponse);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return erupiLinkList;
	}

	@Override
	public ErupiLinkAccountEntity getErupiAccountDetails(String accNumber) {
		// TODO Auto-generated method stub
		return erupiLinkAccountDao.findByErupiLinkAccNumber(accNumber);
	}

	@Override
	public ErupiLinkAccountRequest updateErupiAccountPSFlag(ErupiLinkAccountRequest request) {
		String response="";
		try {			
			int updateAll=erupiLinkAccountRepository.updateAllAsSecondry(request.getOrgId());
			int updateAcctP=erupiLinkAccountRepository.updateAccAsPrimary(request.getOrgId(),request.getAcNumber());
			if(updateAll>0 && updateAcctP>0) {
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			request.setResponse(response);
		}catch (DataIntegrityViolationException e) {
			response=MessageConstant.DUP_ACC;
			request.setResponse(response);
		}
		catch (Exception e) {
			log.error("Error in ErupiLinkAccountServiceImpl......."+e.getMessage());
		}
		return request;
	}

	@Override
	public ErupiLinkAccountRequest getErupiPrimaryAccountDetails(Long orgId) {
		
		String response="";
		ErupiLinkAccountEntity erupiLinkAccountEntity=new ErupiLinkAccountEntity();
		ErupiLinkAccountRequest erupiLinkAccountRequest=new ErupiLinkAccountRequest();
		try {
			erupiLinkAccountEntity=erupiLinkAccountDao.findByErupiPrimaryAccDetails(orgId);
			CopyUtility.copyProperties(erupiLinkAccountEntity,erupiLinkAccountRequest);
			
			ErupiBankMasterEntity erupiBankMasterEntity=bankMasterDao.getDetails(erupiLinkAccountRequest.getBankCode());
			if(erupiBankMasterEntity!=null) {
				erupiLinkAccountRequest.setBankLogo(erupiBankMasterEntity.getBankLogo());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return erupiLinkAccountRequest;
	}

	@Override
	public ErupiLinkAccountRequest updateErupiAccountDisable(ErupiLinkAccountRequest request) {		
		String response="";
		try {			
			int updateAcctP=erupiLinkAccountRepository.updateAccDisable(request.getOrgId(),request.getAcNumber());
			if( updateAcctP>0) {
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			request.setResponse(response);
		}catch (DataIntegrityViolationException e) {
			response=MessageConstant.DUP_ACC;
			request.setResponse(response);
		}
		catch (Exception e) {
			log.error("Error in ErupiLinkAccountServiceImpl......."+e.getMessage());
		}
		return request;
	}

	@Override
	public ErupiLinkAccountRequest updateErupiAccountEnable(ErupiLinkAccountRequest request) {
		String response="";
		try {			
			int updateAcctP=erupiLinkAccountRepository.updateAccEnable(request.getOrgId(),request.getAcNumber());
			if( updateAcctP>0) {
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			request.setResponse(response);
		}catch (DataIntegrityViolationException e) {
			response=MessageConstant.DUP_ACC;
			request.setResponse(response);
		}
		catch (Exception e) {
			log.error("Error in updateErupiAccountEnable......."+e.getMessage());
		}
		return request;
	}

	@Override
	public List<ErupiLinkAccountWithOutResponse> getErupiAccountListDetailsWithStatus(ErupiVoucherCreatedRequest request) {
		List<ErupiLinkAccountEntity> erupiLinkAccountEntity=null;
		String response="";
		ErupiLinkAccountWithOutResponse erupiLinkAccountWithOutResponse=null;
		List<ErupiLinkAccountWithOutResponse> erupiLinkList=new ArrayList<>();
		try {
			erupiLinkAccountEntity=erupiLinkAccountDao.findByErupiLinkOrgIdWithStatus(request.getOrgId());
			for (ErupiLinkAccountEntity erupiLinkAccountWithOutResponse2 : erupiLinkAccountEntity) {
				erupiLinkAccountWithOutResponse=new ErupiLinkAccountWithOutResponse();
				CopyUtility.copyProperties(erupiLinkAccountWithOutResponse2,erupiLinkAccountWithOutResponse);
				erupiLinkList.add(erupiLinkAccountWithOutResponse);
			}
			//start
			List<LinkSubAccountMultipleEntity> linksubacount=linkSubMultipleAccountDao.getLinkMultipleDetailsByOrgId(request.getOrgId());
			for (LinkSubAccountMultipleEntity linkSubAccountMultipleEntity : linksubacount) {
				erupiLinkAccountWithOutResponse=new ErupiLinkAccountWithOutResponse();
				ErupiLinkAccountEntity erupiLinkAccountEntity1=erupiLinkAccountDao.findByErupiLinkById(linkSubAccountMultipleEntity.getErupiLinkAccountEntity().getId());
				CopyUtility.copyProperties(erupiLinkAccountEntity1,erupiLinkAccountWithOutResponse);
				erupiLinkList.add(erupiLinkAccountWithOutResponse);		
			}
			//end
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return erupiLinkList;
	}

	
}
