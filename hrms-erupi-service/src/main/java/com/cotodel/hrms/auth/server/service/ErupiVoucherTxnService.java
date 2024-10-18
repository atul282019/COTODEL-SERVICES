package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherTxnRequest;

public interface ErupiVoucherTxnService {
	
	public ErupiVoucherCreateRequest  saveErupiVoucherTxnDetails(ErupiVoucherCreateRequest request);
	
}
