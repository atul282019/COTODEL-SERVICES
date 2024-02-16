package com.cotodel.hrms.auth.server.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CommonUtility {

	private static final Logger logger = LogManager.getLogger(CommonUtility.class);

	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String ddMMYYYY = "ddMMyyyy";

	public static String userRequest(String sAccessToken,String requestJson,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request Json---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);

			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
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
