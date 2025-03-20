package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherRequestDao;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;
import com.cotodel.hrms.auth.server.service.ErupiVoucherRequestService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiVoucherRequestServiceImpl implements ErupiVoucherRequestService{


	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherRequestServiceImpl.class);
	@Autowired
	ErupiVoucherRequestDao  erupiVoucherRequestDao;
	
	
	@Override
	public ErupiVoucherCreateRequest saveErupiVoucherRequest(ErupiVoucherCreateRequest request) {
		// TODO Auto-generated method stub
		ErupiVoucherCreationRequestEntity erupiVoucherCreationRequestEntity=null;
		String response=MessageConstant.RESPONSE_FAILED;
		try {
			if(request.getAmount()<=0) {
				response=MessageConstant.INSUFBAL;
				request.setResponse(response);
				return request;
			}
			erupiVoucherCreationRequestEntity=new ErupiVoucherCreationRequestEntity();
			CopyUtility.copyProperties(request,erupiVoucherCreationRequestEntity);
			erupiVoucherCreationRequestEntity.setCreationDate(LocalDateTime.now());
			erupiVoucherCreationRequestEntity.setStatus(0);
			erupiVoucherCreationRequestEntity.setStatusMessage("Requested");
			erupiVoucherCreationRequestEntity=erupiVoucherRequestDao.saveDetails(erupiVoucherCreationRequestEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}


	@Override
	public List<ErupiVoucherCreationRequestEntity> getErupiVoucherRequestEmployerId(Long EmployerId,Long EmployeeId) {
		// TODO Auto-generated method stub
		if(EmployerId!=null && EmployerId>0) {
		return erupiVoucherRequestDao.getVoucherCreationRequest(EmployerId);
		}else {
			return erupiVoucherRequestDao.getVoucherCreationRequestEmp(EmployeeId);
		}
	}

}
