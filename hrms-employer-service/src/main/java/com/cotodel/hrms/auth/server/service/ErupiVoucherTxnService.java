package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;
import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;

public interface ErupiVoucherTxnService {
	
	public ErupiVoucherTxnRequest  saveErupiVoucherTxnDetails(ErupiVoucherTxnRequest request);
	public List<ErupiVoucherTxnDetailsEntity>  getErupiVoucherTxnDetails();
	public List<ErupiVoucherTxnDetailsEntity>  getErupiVoucherTxnList(Long orgid);
}
