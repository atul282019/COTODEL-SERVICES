package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ErupiLinkAccountDao;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountRequest;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
import com.cotodel.hrms.auth.server.service.ErupiLinkAccountService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiLinkAccountServiceImpl implements ErupiLinkAccountService{

	@Autowired
	ErupiLinkAccountDao  erupiLinkAccountDao;
	
	@Override
	public ErupiLinkAccountRequest saveErupiAccountDetails(ErupiLinkAccountRequest request) {
		String response="";
		ErupiLinkAccountEntity linkAccountErupiEntity=null;
		try {
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
		}catch (Exception e) {
			log.error("Error in ErupiLinkAccountServiceImpl......."+e.getMessage());
		}
		return request;
	}

	
}
