package com.cotodel.hrms.auth.server.service.impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.DecryptedResponse;
import com.cotodel.hrms.auth.server.dto.DecryptedSmsResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherSmsRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherStatusRequest;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherTxnService;
import com.cotodel.hrms.auth.server.util.ApiRequestSender;
import com.google.gson.Gson;

@Repository
public class ErupiVoucherTxnServiceImpl implements ErupiVoucherTxnService{
	
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherTxnServiceImpl.class);
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	
	
	@Override
	public DecryptedResponse calApiErupiVoucherCreateDetails(ErupiVoucherCreateRequest request) {
		String message="";
		DecryptedResponse decryptedResponse=null;
		try {
			logger.info("In side calApiErupiVoucherCreateDetails:::"+request);
			//PrivateKey privateq=EncryptionUtil.getPrivateKey(applicationConstantConfig.getSignaturePrivatePath);
			//System.out.println(privateq);
			//message=CommonUtility.userRequestForCreateVoucher(applicationConstantConfig.getCreateVouchersToken,applicationConstantConfig.getCreateVouchersMid,createVoucherRequest(request), applicationConstantConfig.getCreateVouchersUrl,applicationConstantConfig.getSignaturePublicPath,applicationConstantConfig.getSignaturePrivatePath);
			message=ApiRequestSender.createRequest(createVoucherRequest(request),applicationConstantConfig.getCreateVouchersUrl,applicationConstantConfig.getSignaturePublicPath,applicationConstantConfig.getCreateVouchersToken,applicationConstantConfig.getSignaturePrivatePath);
			decryptedResponse=message==""?null:jsonToPOJO(message);
			
		} catch (Exception e) {
			logger.error("error Exception ...."+e.getMessage());
			//callApiVoucherCreateResponse.setMessage(e.getMessage());
		}
		return decryptedResponse;
	}
	

	public  String createVoucherRequest(ErupiVoucherCreateRequest req) {
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
	
	public  String voucherStatusRequest(ErupiVoucherStatusRequest req) {
		JSONObject request= new JSONObject();		
		request.put("merchantId", req.getMerchantId());
		request.put("merchantTranId", req.getMerchantTranId());
		request.put("subMerchantId", req.getSubMerchantId());
		request.put("transactionType", "V");
		request.put("terminalId", req.getMcc());
		request.put("UMN", req.getUmn());		
		return request.toString();
	}

	public  DecryptedResponse jsonToPOJO(String json) {
		
		Gson gson = new Gson();
		DecryptedResponse decryptedResponse =new DecryptedResponse();
		try {
			decryptedResponse = gson.fromJson(json, DecryptedResponse.class);
	        //System.out.println("decryptedResponse Name: " + user.isSuccess());
		} catch (Exception e) {
			logger.error("error in CallApiVoucherCreateResponse..."+e.getMessage());
		}
		
        return decryptedResponse;
	}


	@Override
	public DecryptedResponse calApiErupiVoucherStatusDetails(ErupiVoucherStatusRequest request) {
		String message="";
		DecryptedResponse decryptedResponse=null;
		try {
			logger.info("In side calApiErupiVoucherStatusDetails:::"+request);
			
			message=ApiRequestSender.createRequest(voucherStatusRequest(request),applicationConstantConfig.getVoucherStatusUrl,applicationConstantConfig.getSignaturePublicPath,applicationConstantConfig.getCreateVouchersToken,applicationConstantConfig.getSignaturePrivatePath);
			decryptedResponse=message==""?null:jsonToPOJO(message);
			
		} catch (Exception e) {
			logger.error("error Exception ...."+e.getMessage());
			//callApiVoucherCreateResponse.setMessage(e.getMessage());
		}
		return decryptedResponse;
	}

	
	
	@Override
	public DecryptedSmsResponse calApiErupiVoucherSmsDetails(ErupiVoucherSmsRequest request) {
		String message="";
		DecryptedSmsResponse decryptedResponse=null;
		try {
			logger.info("In side calApiErupiVoucherStatusDetails:::"+request);
			
			message=ApiRequestSender.createRequest(voucherSmsRequest(request),applicationConstantConfig.getVoucherSmsUrl,applicationConstantConfig.getSignaturePublicPath,applicationConstantConfig.getCreateVouchersToken,applicationConstantConfig.getSignaturePrivatePath);
			decryptedResponse=message==""?null:jsonToPOJOSms(message);
			
		} catch (Exception e) {
			logger.error("error Exception ...."+e.getMessage());
			//callApiVoucherCreateResponse.setMessage(e.getMessage());
		}
		return decryptedResponse;
	}
	
	public  String voucherSmsRequest(ErupiVoucherSmsRequest req) {
		JSONObject request= new JSONObject();		
		request.put("merchantId", req.getMerchantId());
		request.put("merchantTranId", req.getMerchantTranId());
		request.put("mobileNumber", req.getMobile());
		request.put("beneficiaryID", req.getBeneficiaryId());
		request.put("UMN", req.getUmn());
		request.put("UUID", req.getUuid());
		request.put("SMS_Category", req.getSmsCategory());		
		return request.toString();
	}
	public  DecryptedSmsResponse jsonToPOJOSms(String json) {
		
		Gson gson = new Gson();
		DecryptedSmsResponse decryptedResponse =new DecryptedSmsResponse();
		try {
			decryptedResponse = gson.fromJson(json, DecryptedSmsResponse.class);
	        //System.out.println("decryptedResponse Name: " + user.isSuccess());
		} catch (Exception e) {
			logger.error("error in CallApiVoucherCreateResponse..."+e.getMessage());
		}
		
        return decryptedResponse;
	}
}
