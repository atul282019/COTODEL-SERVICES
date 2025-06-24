package com.cotodel.hrms.auth.server.service.impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;

@Service
public class TokenGeneration {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TokenGeneration.class);

	@Autowired
	ApplicationConstantConfig applicationConstantConfig;

	public String getToken(String url) {
			
			logger.info("opening getInTouch");
			String responseToken="";
			String authToken = "";
	    	try {	    		
	    		String companyId = "HRMS00001";
	    		 responseToken =getToken(companyId,url);
					//String authToken = "";
					if (!ObjectUtils.isEmpty(responseToken)) {
						JSONObject getTokenRes = new JSONObject(responseToken);
						authToken = getTokenRes.getString("access_token");
					}
	    	 
	    	}catch (Exception e) {
				
	    		//e.printStackTrace();
			}
			return authToken;
		}

	public String getToken(String companyId,String url) {
		// TODO Auto-generated method stub
		//System.out.println("url:"+url);
		return getTokenRequest(null,"",companyId,url);
	}
	public static String getTokenRequest(String sAccessToken,String requestJson,String companyId,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request header---"+companyId);
			headers.setContentType(MediaType.APPLICATION_JSON);
			//headers.set("companyId", companyId);
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        returnStr = response.getBody();
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}

}
