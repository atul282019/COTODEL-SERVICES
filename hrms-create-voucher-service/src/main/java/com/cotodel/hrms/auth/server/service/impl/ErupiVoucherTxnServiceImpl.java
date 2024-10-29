package com.cotodel.hrms.auth.server.service.impl;

import java.security.PrivateKey;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.CallApiVoucherCreateResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherTxnService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.EncryptionUtil;
import com.google.gson.Gson;

@Repository
public class ErupiVoucherTxnServiceImpl implements ErupiVoucherTxnService{
	
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherTxnServiceImpl.class);
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	
	
	@Override
	public CallApiVoucherCreateResponse calApiErupiVoucherCreateDetails(ErupiVoucherCreateRequest request) {
		String message="";
		CallApiVoucherCreateResponse callApiVoucherCreateResponse=null;
		try {
			//PrivateKey privateq=EncryptionUtil.getPrivateKey(applicationConstantConfig.getSignaturePrivatePath);
			//System.out.println(privateq);
			message=CommonUtility.userRequestForCreateVoucher(applicationConstantConfig.getCreateVouchersToken,applicationConstantConfig.getCreateVouchersMid,createVoucherRequest(request), applicationConstantConfig.getCreateVouchersUrl,applicationConstantConfig.getSignaturePublicPath,applicationConstantConfig.getSignaturePrivatePath);
			callApiVoucherCreateResponse=message==""?null:jsonToPOJO(message);
		} catch (Exception e) {
			logger.error("error Exception ...."+e.getMessage());
			callApiVoucherCreateResponse.setMessage(e.getMessage());
		}
		return callApiVoucherCreateResponse;
	}
	

	public  String createVoucherRequest(ErupiVoucherCreateRequest req) {
		JSONObject request= new JSONObject();		
		request.put("merchantId", applicationConstantConfig.getCreateVouchersMid);
		request.put("merchantTranId", req.getMerchantTranId());
		request.put("subMerchantId", applicationConstantConfig.getCreateVouchersMid);
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

	public  CallApiVoucherCreateResponse jsonToPOJO(String json) {
		
		Gson gson = new Gson();
		CallApiVoucherCreateResponse user =null;
		try {
			user = gson.fromJson(json, CallApiVoucherCreateResponse.class);
	        //System.out.println("User Name: " + user.isSuccess());
		} catch (Exception e) {
			logger.error("error in CallApiVoucherCreateResponse..."+e.getMessage());
		}
		
        return user;
	}
	public static void main(String[] args) {
		String json="{\r\n"
				+ "\"expiryDate\": \"17-06-2022\",\r\n"
				+ "\"merchantId\": \"400899\",\r\n"
				+ "\"success\": true,\r\n"
				+ "\"response\": \"0\",\r\n"
				+ "\"Amount\": \"202.0\",\r\n"
				+ "\"UMN\": \"EZCb4dfa3cab8c2415787cbc880bff54@prepaidicici\",\r\n"
				+ "\"message\": \"Transaction Successful\",\r\n"
				+ "\"UUID\": \"1a7d50ff7a2545479e4f86910302dca3A300@prepaid.npci\",\r\n"
				+ "\"merchantTranId\": \"55765765765757\",\r\n"
				+ "\"status\": \"CREATE_SUCCESS\"\r\n"
				+ "}";
		ErupiVoucherTxnServiceImpl erupiVoucherTxnServiceImpl=new ErupiVoucherTxnServiceImpl();
		CallApiVoucherCreateResponse response=erupiVoucherTxnServiceImpl.jsonToPOJO(json);
		System.out.println(response);
		
	}
	
}
