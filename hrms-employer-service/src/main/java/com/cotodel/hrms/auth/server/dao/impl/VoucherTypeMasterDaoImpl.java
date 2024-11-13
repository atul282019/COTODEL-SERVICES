package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.VoucherTypeMasterDao;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
import com.cotodel.hrms.auth.server.repository.VoucherTypeMasterRepository;
@Repository
public class VoucherTypeMasterDaoImpl implements VoucherTypeMasterDao{

	@Autowired
	VoucherTypeMasterRepository voucherTypeMasterRepository;

	@Override
	public VoucherTypeMasterEntity saveDetails(VoucherTypeMasterEntity voucherTypeMasterEntity) {
		// TODO Auto-generated method stub
		return voucherTypeMasterRepository.saveAndFlush(voucherTypeMasterEntity);
	}



}
