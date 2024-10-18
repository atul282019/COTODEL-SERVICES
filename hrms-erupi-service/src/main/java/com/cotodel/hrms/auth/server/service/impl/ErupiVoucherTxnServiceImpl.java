package com.cotodel.hrms.auth.server.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherTxnService;
import com.cotodel.hrms.auth.server.util.CommonUtility;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiVoucherTxnServiceImpl implements ErupiVoucherTxnService{

	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public String saveErupiVoucherTxnDetails(ErupiVoucherCreateRequest request) {
		String response="";
		log.info("Starting ErupiVoucherTxnServiceImpl ... saveErupiVoucherTxnDetails..");
		try {
				
		return CommonUtility.userRequestForCreateVoucher(applicationConstantConfig.getCreateVouchersToken,applicationConstantConfig.getCreateVouchersMid,createVoucherRequest(request), applicationConstantConfig.getCreateVouchersUrl);
			
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in ErupiVoucherTxnServiceImpl......."+e.getMessage());
		}
		return response;
	}

	public static String createVoucherRequest(ErupiVoucherCreateRequest req) {
		JSONObject request= new JSONObject();		
		request.put("merchantId", req.getMerchantId());
		request.put("merchantTranId", req.getMerchantTranId());
		request.put("subMerchantId", req.getSubMerchantId());
		request.put("beneficiaryID", req.getBeneficiaryID());
		request.put("mobileNumber", req.getMobileNumber());
		request.put("beneficiaryName", req.getBeneficiaryName());
		request.put("amount", req.getAmount());
		request.put("expiry", req.getExpiry());
		request.put("purposeCode", req.getPurposeCode());
		request.put("mcc", req.getMcc());
		request.put("VoucherRedemptionType", req.getVoucherRedemptionType());
		request.put("PayerVA", req.getPayerVA());
		request.put("type", req.getType());
		
		return request.toString();
	}
}
