package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.DecryptedResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;

public interface ErupiVoucherTxnService {
	
	public DecryptedResponse  calApiErupiVoucherCreateDetails(ErupiVoucherCreateRequest request);
	
}
