package com.cotodel.hrms.auth.server.service.impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.auth.server.dto.DecryptedRedemResponse;
import com.cotodel.hrms.auth.server.dto.EncryptedResponse;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiVoucherRedemService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CommonUtils;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.ResponseDecryption;
import com.cotodel.hrms.auth.server.util.TokenGeneration;
import com.google.gson.Gson;

@Repository
public class ErupiVoucherRedemServiceImpl implements ErupiVoucherRedemService{
	
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherRedemServiceImpl.class);
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public String calApiErupiVoucherRedemDetails(String response) {
		String message="";
		String result="";
		TokenGeneration token=new TokenGeneration();
		DecryptedRedemResponse decryptedRedemResponse=null;
		try {
			logger.info("In side VoucherRedem Impl:::"+response);			
			Gson gson = new Gson();
//			 EncryptedResponse apiResponse = gson.fromJson(response.toString(), EncryptedResponse.class);
//             message=ResponseDecryption.decriptResponse(apiResponse, applicationConstantConfig.getSignaturePrivatePath,200);
			//message=response;
			//Gson gson = new Gson();
            EncryptedResponse apiResponse = gson.fromJson(response.toString(), EncryptedResponse.class);
            message=ResponseDecryption.decriptResponse(apiResponse, applicationConstantConfig.getSignaturePrivatePath,200);
			decryptedRedemResponse=message==""?null:jsonToPojoRedem(message);
			
			String request= getRequestRedem(decryptedRedemResponse);
			
             String tokenvalue = token.getToken(applicationConstantConfig.authTokenApiUrl+CommonUtils.getToken);
 			
 			
 			String response1 = CommonUtility.userRequest(tokenvalue, request,
 					applicationConstantConfig.empServiceApiUrl+CommonUtils.erupiVoucherRedem);
 			if (!ObjectUtils.isEmpty(response1)) {
 				JSONObject demoRes = new JSONObject(response1);
 				boolean status = demoRes.getBoolean("status");
 				if (status) {
 					result="Success";
 					logger.info("In side VoucherRedem Impl:::Save :Success"+response);	
 				}else {
 					result="Fail";
 					logger.info("In side VoucherRedem Impl:::Save :Fail"+response);	
 				}
 			}else {
 				result="Fail";
					logger.info("In side VoucherRedem Impl:::Save :Fail"+response);	
 			}
			
		} catch (Exception e) {
			logger.error("error VoucherRedem Impl ...."+e.getMessage());
		}
		return result;
	}



	
	public  DecryptedRedemResponse jsonToPojoRedem(String json) {
		
		//Gson gson = new Gson();
		DecryptedRedemResponse decryptedResponse =new DecryptedRedemResponse();
		try {
			decryptedResponse = MessageConstant.gson.fromJson(json, DecryptedRedemResponse.class);
	        			
		} catch (Exception e) {
			logger.error("error in jsonToPojoRedem..."+e.getMessage());
		}
		
        return decryptedResponse;
	}
	
	
	public  String getRequestRedem(DecryptedRedemResponse req) {
		JSONObject request= new JSONObject();		
		request.put("merchantId", req.getMerchantId());
		request.put("subMerchantId", req.getSubMerchantId());
		request.put("terminalId", req.getTerminalId());
		request.put("bankRRN", req.getBankRRN());
		request.put("merchantTranId", req.getMerchantTranId());
		request.put("payerName", req.getPayerName());
		request.put("payerMobile", req.getPayerMobile());
		request.put("payerVA", req.getPayerVA());
		request.put("payerAmount", req.getPayerAmount());
		request.put("txnStatus", req.getTxnStatus());
		request.put("responseCode", req.getResponseCode());
		request.put("txnInitDate", req.getTxnInitDate());
		request.put("txnCompletionDate", req.getTxnCompletionDate());
		request.put("umn", req.getUMN());
		request.put("payeeName", req.getPayeeName());
		return request.toString();
	}
}
