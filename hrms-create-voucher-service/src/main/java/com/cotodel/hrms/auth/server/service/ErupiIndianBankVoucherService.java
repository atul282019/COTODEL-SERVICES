package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.ErupiIndianBankVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiIndianBankVoucherInquiryRequest;
import com.cotodel.hrms.auth.server.dto.ErupiIndianBankVoucherRevokeRequest;
import com.cotodel.hrms.auth.server.dto.IndianBankVoucherCreateResponse;

public interface ErupiIndianBankVoucherService {
	public IndianBankVoucherCreateResponse  calApiErupiVoucherCreateDetails(ErupiIndianBankVoucherCreateRequest request);
	public IndianBankVoucherCreateResponse  calApiErupiVoucherRevokeDetails(ErupiIndianBankVoucherRevokeRequest request);
	public IndianBankVoucherCreateResponse  calApiErupiVoucherInquiryDetails(ErupiIndianBankVoucherInquiryRequest request);
}
