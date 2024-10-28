package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.CallApiVoucherCreateResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;

public interface ErupiVoucherTxnService {
	
	public CallApiVoucherCreateResponse  calApiErupiVoucherCreateDetails(ErupiVoucherCreateRequest request);
	
}
