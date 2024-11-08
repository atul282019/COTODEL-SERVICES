package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ErupiLinkAccountDao;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountRequest;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountWithOutResponse;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
import com.cotodel.hrms.auth.server.repository.EmployeeOnboardingRepository;
import com.cotodel.hrms.auth.server.service.ErupiLinkAccountService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiLinkAccountServiceImpl implements ErupiLinkAccountService{

	@Autowired
	ErupiLinkAccountDao  erupiLinkAccountDao;
	
	@Autowired
	EmployeeOnboardingRepository employeeOnboardingRepository;
	
	@Override
	public ErupiLinkAccountRequest saveErupiAccountDetails(ErupiLinkAccountRequest request) {
		String response="";
		ErupiLinkAccountEntity linkAccountErupiEntity=null;
		List<EmployeeOnboardingEntity> list=new ArrayList<>();
		try {
			
			list=employeeOnboardingRepository.findByOnboardingList(request.getOrgId());
			if(list==null || list.size()==0) {
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

	
}
