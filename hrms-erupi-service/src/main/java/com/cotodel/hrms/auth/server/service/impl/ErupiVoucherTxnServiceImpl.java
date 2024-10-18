package com.cotodel.hrms.auth.server.service.impl;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.service.ErupiVoucherTxnService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiVoucherTxnServiceImpl implements ErupiVoucherTxnService{


	
	@Override
	public ErupiVoucherCreateRequest saveErupiVoucherTxnDetails(ErupiVoucherCreateRequest request) {
		String response="";
		log.info("Starting ErupiVoucherTxnServiceImpl ... saveErupiVoucherTxnDetails..");
		try {
				
		
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in ErupiVoucherTxnServiceImpl......."+e.getMessage());
		}
		return request;
	}

	
}
