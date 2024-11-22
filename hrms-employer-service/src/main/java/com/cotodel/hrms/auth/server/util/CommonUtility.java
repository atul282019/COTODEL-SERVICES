package com.cotodel.hrms.auth.server.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherSummaryDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;



public class CommonUtility {
	
	private static final Logger logger = LogManager.getLogger(CommonUtility.class);
	
	
	public static String userRequest(String sAccessToken,String requestJson,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request Json for url"+url+"---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);
			
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
			
			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(HttpStatusCodeException e) {
			logger.error("HttpStatusCodeException error in---"+url+"-"+e.getResponseBodyAsString());
			return e.getResponseBodyAsString();
		}catch(Exception e){
			logger.error(" error in---"+url+"-"+e);
			return null;
		}finally {
			restTemplate=null;headers=null;sAccessToken=null;requestJson=null;url=null;	
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
			//e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}

	public static Long getCount(List<ErupiVoucherSummaryDto> list) {
		 Long totolCount=0l;
		  for(ErupiVoucherSummaryDto dto:list) {
			  Long count=dto.getCount();
			  totolCount=totolCount+count;
		  }
		 return totolCount;
	}
		 public static Long getAmount(List<ErupiVoucherSummaryDto> list) {
			 Long totolAmount=0l;
			  for(ErupiVoucherSummaryDto dto:list) {
				  Long amount=dto.getTotalAmount();
				  totolAmount=totolAmount+amount;
			  }
			 return totolAmount;
		 
	 }
		 
		 public static Long getCreateCount(List<ErupiVoucherCreateSummaryDto> list) {
			 Long totolCount=0l;
			  for(ErupiVoucherCreateSummaryDto dto:list) {
				  Long count=dto.getCount();
				  totolCount=totolCount+count;
			  }
			 return totolCount;
		}
			 public static Long getCreateAmount(List<ErupiVoucherCreateSummaryDto> list) {
				 Long totolAmount=0l;
				  for(ErupiVoucherCreateSummaryDto dto:list) {
					  Long amount=dto.getTotalAmount();
					  totolAmount=totolAmount+amount;
				  }
				 return totolAmount;
			 
		 }
	
}
