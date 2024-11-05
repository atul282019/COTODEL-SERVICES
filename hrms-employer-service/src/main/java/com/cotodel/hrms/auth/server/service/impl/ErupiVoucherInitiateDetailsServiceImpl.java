package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherInitiateDetailsDao;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherInitiateDetailsRequest;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationDetailsEntity;
import com.cotodel.hrms.auth.server.service.ErupiVoucherInitiateDetailsService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiVoucherInitiateDetailsServiceImpl implements ErupiVoucherInitiateDetailsService{

	@Autowired
	ErupiVoucherInitiateDetailsDao  erupiVoucherInitiateDetailsDao;
	
	@Override
	public ErupiVoucherInitiateDetailsRequest saveErupiVoucherInitiateDetails(ErupiVoucherInitiateDetailsRequest request) {
		String response="";
		log.info("Starting ErupiVoucherInitiateDetailsServiceImpl ... saveErupiVoucherInitiateDetails..");
		ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity=null;
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			erupiVoucherInitiateDetailsEntity=new ErupiVoucherCreationDetailsEntity();
			CopyUtility.copyProperties(request,erupiVoucherInitiateDetailsEntity);

			LocalDate eventDate = LocalDate.now();	
			erupiVoucherInitiateDetailsEntity.setCreationDate(eventDate);
			erupiVoucherInitiateDetailsEntity=erupiVoucherInitiateDetailsDao.saveDetails(erupiVoucherInitiateDetailsEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in ErupiVoucherInitiateDetailsServiceImpl......."+e.getMessage());
		}
		return request;
	}

	
}
