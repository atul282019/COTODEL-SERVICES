package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;

public interface ErupiVoucherTxnService {
	
	public String  saveErupiVoucherTxnDetails(ErupiVoucherCreateRequest request);
	
}
