package com.cotodel.hrms.auth.server.service.impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.DecryptedResponse;
import com.cotodel.hrms.auth.server.dto.ErupiIndianBankVoucherCreateRequest;
import com.cotodel.hrms.auth.server.dto.ErupiIndianBankVoucherInquiryRequest;
import com.cotodel.hrms.auth.server.dto.ErupiIndianBankVoucherRevokeRequest;
import com.cotodel.hrms.auth.server.dto.IndianBankVoucherCreateResponse;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.ErupiIndianBankVoucherService;
import com.cotodel.hrms.auth.server.util.CheckSumGeneratorUtil;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.EncryptionDecryptionBradCo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Repository
public class ErupiIndianBankVoucherServiceImpl implements ErupiIndianBankVoucherService{
	
	private static final Logger logger = LoggerFactory.getLogger(ErupiIndianBankVoucherServiceImpl.class);
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;	
	
	
	@Override
	public IndianBankVoucherCreateResponse calApiErupiVoucherCreateDetails(ErupiIndianBankVoucherCreateRequest request) {
		String response="";
		DecryptedResponse decryptedResponse=null;
		IndianBankVoucherCreateResponse indian=null;
		try {
			logger.info("In side calApiErupiVoucherCreateDetails:::"+request);
			String checksum=CheckSumGeneratorUtil.getVoucherCreationCheckSum(request.getCorpmobno(), request.getCorpupiID(), request.getCorpaccNo(), applicationConstantConfig.erupiIndianbankVoucherKey);
			request.setChecksum(checksum);
			String datajson=createIndianVoucherRequest(request);
			String data=EncryptionDecryptionBradCo.voucherCreate(datajson, applicationConstantConfig.erupiIndianbankVoucherEndeKey);
			String json=convertJson(data);
			response=CommonUtility.createVoucher(json, applicationConstantConfig.erupiIndianbankVoucherCreateUrl);
			indian=jsonToPOJO(response);
			System.out.println(indian.toString());
		} catch (Exception e) {
			logger.error("error Exception ...."+e.getMessage());
		}
		return indian;
	}
		public  String convertJson(String data) {
			JSONObject request= new JSONObject();		
			request.put("data",data);			
			return request.toString();
		}
	 
     public  String createIndianVoucherRequest(ErupiIndianBankVoucherCreateRequest req) {
    	 ObjectMapper objectMapper = new ObjectMapper();
    	 String json ="";
    	 try {
    		 json = objectMapper.writeValueAsString(req);
		} catch (Exception e) {
			// TODO: handle exception
		}
         // Print the JSON string
         System.out.println(json);
         return json;
 	}
     public  String revokeIndianVoucherRequest(ErupiIndianBankVoucherRevokeRequest req) {
    	 ObjectMapper objectMapper = new ObjectMapper();
    	 String json ="";
    	 try {
    		 json = objectMapper.writeValueAsString(req);
		} catch (Exception e) {
			// TODO: handle exception
		}
         // Print the JSON string
         System.out.println(json);
         return json;
 	}
     public  String inquiryIndianVoucherRequest(ErupiIndianBankVoucherInquiryRequest req) {
    	 ObjectMapper objectMapper = new ObjectMapper();
    	 String json ="";
    	 try {
    		 json = objectMapper.writeValueAsString(req);
		} catch (Exception e) {
			// TODO: handle exception
		}
         // Print the JSON string
         System.out.println(json);
         return json;
 	}
     
     
	@Override
	public IndianBankVoucherCreateResponse calApiErupiVoucherRevokeDetails(ErupiIndianBankVoucherRevokeRequest request) {
		
		String response="";
		DecryptedResponse decryptedResponse=null;
		IndianBankVoucherCreateResponse indian=null;
		try {
			logger.info("In side calApiErupiVoucherCreateDetails:::"+request);
			String checksum=CheckSumGeneratorUtil.getVoucherRevokeCheckSum(request.getInputparam().getUmn(), request.getInputparam().getOrgTxnAmount(),applicationConstantConfig.erupiIndianbankVoucherKey);
			request.getInputparam().setCheckSum(checksum);
			String datajson=revokeIndianVoucherRequest(request);
			String data=EncryptionDecryptionBradCo.voucherRevoke(datajson, applicationConstantConfig.erupiIndianbankVoucherEndeKey);
			String json=convertJson(data);
			response=CommonUtility.createVoucher(json, applicationConstantConfig.erupiIndianbankVoucherRevokeUrl);
			indian=jsonToPOJO(response);
			System.out.println(indian.toString());
		} catch (Exception e) {
			logger.error("error Exception ...."+e.getMessage());
		}
		return indian;
	}
	
	@Override
	public IndianBankVoucherCreateResponse calApiErupiVoucherInquiryDetails(ErupiIndianBankVoucherInquiryRequest request) {
		String response="";
		IndianBankVoucherCreateResponse indian=null;
		try {
			logger.info("In side calApiErupiVoucherCreateDetails:::"+request);
			String checksum=CheckSumGeneratorUtil.getVoucherInquiryCheckSum(request.getInputparam().getUmn(), request.getInputparam().getOrgTxnAmount(),applicationConstantConfig.erupiIndianbankVoucherKey);
			request.getInputparam().setCheckSum(checksum);
			String datajson=inquiryIndianVoucherRequest(request);
			String data=EncryptionDecryptionBradCo.voucherInquiry(datajson, applicationConstantConfig.erupiIndianbankVoucherEndeKey);
			String json=convertJson(data);
			response=CommonUtility.createVoucher(json, applicationConstantConfig.erupiIndianbankVoucherInquiryUrl);
			indian=jsonToPOJO(response);
			//System.out.println(indian.toString());
		} catch (Exception e) {
			logger.error("error Exception ...."+e.getMessage());
		}
		return indian;
	}
	

	
	public  IndianBankVoucherCreateResponse jsonToPOJO(String json) {
		
		Gson gson = new Gson();
		IndianBankVoucherCreateResponse decryptedResponse =new IndianBankVoucherCreateResponse();
		try {
			decryptedResponse = gson.fromJson(json, IndianBankVoucherCreateResponse.class);
	        //System.out.println("decryptedResponse Name: " + user.isSuccess());
		} catch (Exception e) {
			logger.error("error in CallApiVoucherCreateResponse..."+e.getMessage());
		}
		
        return decryptedResponse;
	}
	
}

