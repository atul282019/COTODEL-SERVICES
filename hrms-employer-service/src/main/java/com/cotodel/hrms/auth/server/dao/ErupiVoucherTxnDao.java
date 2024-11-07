package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;

public interface ErupiVoucherTxnDao {
	public ErupiVoucherTxnDetailsEntity saveDetails(ErupiVoucherTxnDetailsEntity erupiVoucherTxnEntity);
	public List<ErupiVoucherTxnDetailsEntity> getVoucherTxnDetails();
	
}
