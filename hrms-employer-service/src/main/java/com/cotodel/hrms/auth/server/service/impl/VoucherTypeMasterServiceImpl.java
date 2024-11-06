package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import com.cotodel.hrms.auth.server.repository.BankMasterRepository;
import com.cotodel.hrms.auth.server.repository.VoucherTypeMasterRepository;
import com.cotodel.hrms.auth.server.service.VoucherTypeMasterService;
@Repository
public class VoucherTypeMasterServiceImpl implements VoucherTypeMasterService{

	@Autowired
	VoucherTypeMasterRepository voucherTypeMasterRepository;

	@Override
	public List<VoucherTypeMasterEntity> getVoucherTypeMaster() {
		// TODO Auto-generated method stub
		return voucherTypeMasterRepository.findAll();
	}
	
	
	
}
