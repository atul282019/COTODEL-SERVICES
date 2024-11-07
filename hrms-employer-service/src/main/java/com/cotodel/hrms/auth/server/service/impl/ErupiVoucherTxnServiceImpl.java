package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherTxnDao;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;
import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
import com.cotodel.hrms.auth.server.model.WorkFlowMasterEntity;
import com.cotodel.hrms.auth.server.repository.WorkFlowMasterRepository;
import com.cotodel.hrms.auth.server.service.ErupiVoucherTxnService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiVoucherTxnServiceImpl implements ErupiVoucherTxnService{

	@Autowired
	ErupiVoucherTxnDao  erupiVoucherTxnDao;
	
	@Autowired
	WorkFlowMasterRepository  workFlowMasterRepository;
	
	@Override
	public ErupiVoucherTxnRequest saveErupiVoucherTxnDetails(ErupiVoucherTxnRequest request) {
		String response="";
		log.info("Starting ErupiVoucherTxnServiceImpl ... saveErupiVoucherTxnDetails..");
		ErupiVoucherTxnDetailsEntity erupiVoucherTxnEntity=null;
		WorkFlowMasterEntity workFlowMasterEntity=new WorkFlowMasterEntity();
		try {
			response=MessageConstant.RESPONSE_FAILED;
			workFlowMasterEntity=workFlowMasterRepository.findByWorkFlowId(request.getWorkFlowId());
			if(workFlowMasterEntity==null) {
				response=MessageConstant.WORANG_WFID;
				request.setResponseMsg(response);
				return request;
			}
			erupiVoucherTxnEntity=new ErupiVoucherTxnDetailsEntity();
			CopyUtility.copyProperties(request,erupiVoucherTxnEntity);
			request.setResponseMsg(response);
			//erupiVoucherTxnEntity.setWorkFlowId(request.getWorkFlowId());
			LocalDateTime eventDate = LocalDateTime.now();	
			erupiVoucherTxnEntity.setCreationDate(eventDate);
			erupiVoucherTxnEntity=erupiVoucherTxnDao.saveDetails(erupiVoucherTxnEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponseMsg(response);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in ErupiVoucherTxnServiceImpl......."+e.getMessage());
		}
		return request;
	}

	@Override
	public List<ErupiVoucherTxnDetailsEntity> getErupiVoucherTxnDetails() {
		// TODO Auto-generated method stub
		return erupiVoucherTxnDao.getVoucherTxnDetails();
	}

	
}
