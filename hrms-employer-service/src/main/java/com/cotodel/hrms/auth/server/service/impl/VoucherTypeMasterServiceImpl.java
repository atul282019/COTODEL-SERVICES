package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.controller.ExpenseTravelController;
import com.cotodel.hrms.auth.server.dao.VoucherTypeMasterDao;
import com.cotodel.hrms.auth.server.dto.VoucherTypeDto;
import com.cotodel.hrms.auth.server.dto.VoucherTypeMasterRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import com.cotodel.hrms.auth.server.repository.VoucherTypeMasterRepository;
import com.cotodel.hrms.auth.server.service.VoucherTypeMasterService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class VoucherTypeMasterServiceImpl implements VoucherTypeMasterService{
	private static final Logger logger = LoggerFactory.getLogger(VoucherTypeMasterServiceImpl.class);
	@Autowired
	VoucherTypeMasterRepository voucherTypeMasterRepository;
	
	@Autowired
	VoucherTypeMasterDao voucherTypeMasterDao;

//	@Override
//	public List<VoucherTypeMasterEntity> getVoucherTypeMaster() {
//		
//		return voucherTypeMasterRepository.findVoucherTypeMasterList();
//	}

//	@Override
//	public List<VoucherTypeDto> getVoucherTypeList() {
//		
//		return voucherTypeMasterRepository.findAllUserDTOs();
//	}

	@Override
	public VoucherTypeMasterEntity getVoucherTypeMasterDetail(String voucherCode) {
		
		return voucherTypeMasterRepository.findVoucherTypeMasterDetail(voucherCode);
	}

	@Override
	public VoucherTypeMasterRequest saveVoucherTypeMaster(VoucherTypeMasterRequest request) {
		
		String response="";
		VoucherTypeMasterEntity voucherTypeMasterEntity=null;
		try {
			
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			voucherTypeMasterEntity=new VoucherTypeMasterEntity();
			CopyUtility.copyProperties(request,voucherTypeMasterEntity);
			
			LocalDateTime eventDate = LocalDateTime.now();	
			voucherTypeMasterEntity.setCreationDate(eventDate);
			voucherTypeMasterEntity.setActiveStatus("1");
			voucherTypeMasterEntity.setStatus(1l);
			voucherTypeMasterEntity=voucherTypeMasterDao.saveDetails(voucherTypeMasterEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		}catch (DataIntegrityViolationException e) {
			response=MessageConstant.DUP_VR_CODE;
			request.setResponse(response);
		}
		catch (Exception e) {
			logger.error("Error in ErupiLinkAccountServiceImpl......."+e.getMessage());
		}
		return request;
	}

	@Override
	public VoucherTypeMasterRequest updateVoucherTypeMaster(VoucherTypeMasterRequest request) {
		String response="";
		try {			
			response=MessageConstant.RESPONSE_FAILED;
			int updateAll=0;
			if(request.getStatus()==1) {
				updateAll=voucherTypeMasterRepository.updateActiveStatus(request.getId(),"0",0l);
			}else {
				updateAll=voucherTypeMasterRepository.updateActiveStatus(request.getId(),"1",1l);
			}
			if(updateAll>0) {
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			request.setResponse(response);
		}catch (DataIntegrityViolationException e) {
			response=MessageConstant.DUP_ACC;
			request.setResponse(response);
		}
		catch (Exception e) {
			logger.error("Error in updateVoucherTypeMaster......."+e.getMessage());
		}
		return request;
	}
	
	
	
}
