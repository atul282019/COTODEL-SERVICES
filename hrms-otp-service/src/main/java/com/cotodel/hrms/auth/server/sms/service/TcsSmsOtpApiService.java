package com.cotodel.hrms.auth.server.sms.service;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.config.token.NhaBisAccessTokenService;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;

@Service
public class TcsSmsOtpApiService {


	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	public NhaBisAccessTokenService nhaBisAccessTokenService;
	
	
	private static final Logger logger = LogManager.getLogger(TcsSmsOtpApiService.class);
	
	public String sendSms(String mobile,String otp,String txn) {
		
		
		String res=sentSmsRequest(nhaBisAccessTokenService.getTokenOauth2_0(applicationConstantConfig.TCS_SMS_GATEWAY_CLIENT_ID,
				applicationConstantConfig.TCS_SMS_GATEWAY_CLIENT_SECRET,
				applicationConstantConfig.TCS_SMS_GATEWAY_TOKEN_URL),
				getRequest(mobile,otp,txn), applicationConstantConfig.TCS_SMS_GATEWAY_URL);
		
		logger.info("res=="+res);
		
		return res;
		
	}
	
	public String getRequest(String mobile,String otp,String txn) {
		
		JSONObject request= new JSONObject();
		
		request.put("userName", applicationConstantConfig.TCS_SMS_GATEWAY_USERNAME); 
		request.put ("userPass",applicationConstantConfig.TCS_SMS_GATEWAY_PASSWORD); 
		request.put("mobileNo", mobile);
		request.put("message", "Use "+otp+" as OTP for login on BIS Portal, Reference No. kf234. This OTP is valid for 10 minutes.NHA"); 
		request.put("msgType","ENG"); 
		request.put("dlt_template_id",applicationConstantConfig.TCS_SMS_GATEWAY_dlt_template_id); 
		
		logger.info("Massge Request===="+request.toString());
		
		return request.toString();
		
	}
	
	public   String sentSmsRequest(String sAccessToken,String requestJson,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		try{

			logger.info("requestJson===="+requestJson);
			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(sAccessToken);
		
			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			
			logger.info("res=="+returnStr);
		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return returnStr;
	}



}
