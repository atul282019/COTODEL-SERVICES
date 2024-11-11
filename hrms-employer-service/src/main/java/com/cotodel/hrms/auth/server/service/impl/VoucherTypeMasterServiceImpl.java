package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.VoucherTypeDto;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import com.cotodel.hrms.auth.server.repository.VoucherTypeMasterRepository;
import com.cotodel.hrms.auth.server.service.VoucherTypeMasterService;
@Repository
public class VoucherTypeMasterServiceImpl implements VoucherTypeMasterService{

	@Autowired
	VoucherTypeMasterRepository voucherTypeMasterRepository;

	@Override
	public List<VoucherTypeMasterEntity> getVoucherTypeMaster() {
		
		return voucherTypeMasterRepository.findAll();
	}

	@Override
	public List<VoucherTypeDto> getVoucherTypeList() {
		
		return voucherTypeMasterRepository.findAllUserDTOs();
	}

	@Override
	public VoucherTypeMasterEntity getVoucherTypeMasterDetail(String voucherCode) {
		// TODO Auto-generated method stub
		return voucherTypeMasterRepository.findVoucherTypeMasterDetail(voucherCode);
	}
	
	
	
}
