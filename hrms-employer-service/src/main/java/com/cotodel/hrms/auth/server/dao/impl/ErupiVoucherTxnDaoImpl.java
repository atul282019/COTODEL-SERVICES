package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherTxnDao;
import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
import com.cotodel.hrms.auth.server.repository.ErupiVoucherTxnRepository;
@Repository
public class ErupiVoucherTxnDaoImpl implements ErupiVoucherTxnDao{

	@Autowired
	ErupiVoucherTxnRepository erupiVoucherTxnRepository;

	@Override
	public ErupiVoucherTxnDetailsEntity saveDetails(ErupiVoucherTxnDetailsEntity erupiVoucherTxnEntity) {
		// TODO Auto-generated method stub
		return erupiVoucherTxnRepository.saveAndFlush(erupiVoucherTxnEntity);
	}	
	
}
