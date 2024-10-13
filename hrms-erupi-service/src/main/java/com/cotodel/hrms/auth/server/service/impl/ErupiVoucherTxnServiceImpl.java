package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;
import com.cotodel.hrms.auth.server.service.ErupiVoucherTxnService;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiVoucherTxnServiceImpl implements ErupiVoucherTxnService{

//	@Autowired
//	ErupiVoucherTxnDao  erupiVoucherTxnDao;
	
	@Override
	public ErupiVoucherTxnRequest saveErupiVoucherTxnDetails(ErupiVoucherTxnRequest request) {
		String response="";
		log.info("Starting ErupiVoucherTxnServiceImpl ... saveErupiVoucherTxnDetails..");
		//ErupiVoucherTxnEntity erupiVoucherTxnEntity=null;
		try {
			response=MessageConstant.RESPONSE_FAILED;
				
		//	erupiVoucherTxnEntity=new ErupiVoucherTxnEntity();
		//	CopyUtility.copyProperties(request,erupiVoucherTxnEntity);
			request.setResponse(response);
			LocalDateTime eventDate = LocalDateTime.now();	
//			erupiVoucherTxnEntity.setCreationDate(eventDate);
//			erupiVoucherTxnEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in ErupiVoucherTxnServiceImpl......."+e.getMessage());
		}
		return request;
	}

	
}
