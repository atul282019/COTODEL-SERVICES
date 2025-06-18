package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusRedeemResponse;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusRequest;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherStatusSmsRequest;

public interface ErupiVoucherStatusSmsService {	
	public ErupiVoucherStatusSmsRequest  erupiVoucherSmsDetails(ErupiVoucherStatusSmsRequest request);
	
	public ErupiVoucherStatusRequest  erupiVoucherStatusDetails(ErupiVoucherStatusRequest request);
	public ErupiVoucherStatusRedeemResponse  erupiVoucherStatusDetailsHistory(ErupiVoucherStatusRequest request);
	
}
