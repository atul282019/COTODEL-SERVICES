package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.DecryptedResponse;
import com.cotodel.hrms.auth.server.dto.DecryptedSmsResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSmsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherStatusRequest;

public interface ErupiVoucherTxnService {
	
	public DecryptedResponse  calApiErupiVoucherCreateDetails(ErupiVoucherCreateRequest request);
	public DecryptedResponse  calApiErupiVoucherStatusDetails(ErupiVoucherStatusRequest request);
	public DecryptedSmsResponse  calApiErupiVoucherSmsDetails(ErupiVoucherSmsRequest request);

	
}
