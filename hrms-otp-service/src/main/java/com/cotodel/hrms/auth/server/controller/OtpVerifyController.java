package com.cotodel.hrms.auth.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.dto.SmsOtpDto;
import com.cotodel.hrms.auth.server.dto.SmsParam;
import com.cotodel.hrms.auth.server.redis.cache.manager.OtpCacheManager;
import com.cotodel.hrms.auth.server.response.OtpServiceResponse;
import com.cotodel.hrms.auth.server.util.TransactionManager;
@RestController
@RequestMapping("/Api")
@CrossOrigin
public class OtpVerifyController {

	
private static final Logger logger = LoggerFactory.getLogger(OtpVerifyController.class);
	

@Autowired
public OtpCacheManager otpCacheManager;
	
	
	@PostMapping(value="/otp/verify/1.0", produces=MediaType.APPLICATION_JSON_VALUE)
	public String smsVerifyRequest(@RequestBody SmsParam smsParam) {
		
		try {
			if(!ObjectUtils.isEmpty(smsParam)) {
				SmsOtpDto smsDto= new SmsOtpDto();
				
				
				if(smsParam.getTemplateid().equalsIgnoreCase("1007163593301170578"))
					smsDto=	otpCacheManager.getStateDetails("A_"+smsParam.getMobile());
				else if(smsParam.getTemplateid().equalsIgnoreCase("1007163593280345232"))
					smsDto=	otpCacheManager.getStateDetails("D_"+smsParam.getMobile());
				else if(smsParam.getTemplateid().equalsIgnoreCase("1007163593275058061"))
					smsDto=	otpCacheManager.getStateDetails("L_"+smsParam.getMobile());
				else if(smsParam.getTemplateid().equalsIgnoreCase("1007164684043366664"))
					smsDto=	otpCacheManager.getStateDetails("U_"+smsParam.getMobile());
				
				logger.info("smsDto====="+smsDto.getOtp());
				logger.info("smsParam====="+smsParam.getOtp());
				
				logger.info("smsParam====="+smsParam.getMobile());
				
				if(smsDto.getOtp().equalsIgnoreCase(smsParam.getOtp())) {
					
					if(smsParam.getTemplateid().equalsIgnoreCase("1007163593301170578"))
						otpCacheManager.deleteOtp("A_"+smsParam.getMobile(), smsDto);
					else if(smsParam.getTemplateid().equalsIgnoreCase("1007163593280345232"))
						otpCacheManager.deleteOtp("D_"+smsParam.getMobile(), smsDto);
					else if(smsParam.getTemplateid().equalsIgnoreCase("1007163593275058061"))
						otpCacheManager.deleteOtp("L_"+smsParam.getMobile(), smsDto);
					else if(smsParam.getTemplateid().equalsIgnoreCase("1007164684043366664"))
						otpCacheManager.deleteOtp("U_"+smsParam.getMobile(), smsDto);
					
				
					
					return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "true", "200", "success")	;	
					
				}else {
					return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "false", "201", "Incorrect OTP")	;
				}
			
			}else {
				return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "false", "201", "Incorrect OTP")	;
			}
			
			
			
		}catch (Exception e) {
			logger.info(e.getMessage());// TODO: handle exception
			return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "false", "201", "Incorrect OTP")	;
		}
		
		
		
		
			
		
	}



}
