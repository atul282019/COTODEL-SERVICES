package com.cotodel.hrms.auth.server.controller;

import java.util.Date;

import org.json.JSONObject;
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
import com.cotodel.hrms.auth.server.model.SmsOtpEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.redis.cache.manager.OtpCacheManager;
import com.cotodel.hrms.auth.server.response.OtpServiceResponse;
import com.cotodel.hrms.auth.server.service.SmsOtpService;
import com.cotodel.hrms.auth.server.sms.service.SmsOtpApiService2;
import com.cotodel.hrms.auth.server.sms.service.TemplateService;
import com.cotodel.hrms.auth.server.util.GenerateOtpFunction;
import com.cotodel.hrms.auth.server.util.StrListFunction;
import com.cotodel.hrms.auth.server.util.TransactionManager;

@RestController
@RequestMapping("/Api")
@CrossOrigin
public class OtpSenderController {

	private static final Logger logger = LoggerFactory.getLogger(OtpSenderController.class);

	@Autowired
	public OtpCacheManager otpCacheManager;

	@Autowired
	public SmsOtpApiService2 smsOtpApiService;

	@Autowired
	public SmsOtpService smsOtpService;

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	public TemplateService templateService;

	@PostMapping(value="/otp/sender/1.0", produces=MediaType.APPLICATION_JSON_VALUE)
	public String smsSenderRequest(@RequestBody SmsParam smsParam) {
		String otp="";
		String sendRes="";
		try {
			String txn=TransactionManager.getTransactionId();
			logger.info(txn);
			logger.info("mobile :"+smsParam.getMobile());

			if(!ObjectUtils.isEmpty(smsParam)) {

				if(StrListFunction.convertArrayToList(applicationConstantConfig.BIS_SMS_GATEWAY_OTP_LIST.split(",")).contains(smsParam.getTemplateid())) {


					SmsOtpDto smsDto= new SmsOtpDto();


					otp =GenerateOtpFunction.generateOTP();



					smsDto.setMobile(smsParam.getMobile());
					smsDto.setOtp(otp);

					if(smsParam.getTemplateid().equalsIgnoreCase("1007163593301170578"))
						otpCacheManager.cacheStateDetails("A_"+smsParam.getMobile(), smsDto);
					else if(smsParam.getTemplateid().equalsIgnoreCase("1007163593280345232"))
						otpCacheManager.cacheStateDetails("D_"+smsParam.getMobile(), smsDto);
					else if(smsParam.getTemplateid().equalsIgnoreCase("1007163593275058061"))
						otpCacheManager.cacheStateDetails("L_"+smsParam.getMobile(), smsDto);
					else if(smsParam.getTemplateid().equalsIgnoreCase("1007164684043366664"))
						otpCacheManager.cacheStateDetails("U_"+smsParam.getMobile(), smsDto);

					//	String sms="Dear User, Your OTP to access NHA BIS portal is "+otp+". It will be valid for 3 minutes.NHA";

					String sms=templateService.getTemplateSms(smsParam.getTemplateid()).replace("{#var#}", otp);

					if(applicationConstantConfig.SMS_GATEWAY.equalsIgnoreCase("1")) {

						sendRes=	smsOtpApiService.sendSms(sms, smsParam.getMobile(),smsParam.getTemplateid());
						JSONObject resJson=new JSONObject(sendRes);
						if(!ObjectUtils.isEmpty(resJson)) {

							if(resJson.getString("STATUS").equalsIgnoreCase("OK")) {

								JSONObject response=resJson.getJSONObject("RESPONSE");
								if(!ObjectUtils.isEmpty(response)) {
									if(response.getString("INFO").equalsIgnoreCase("SUBMITTED")) {
										SmsOtpEntity smsOtp= new SmsOtpEntity();
										smsOtp.setMobile(smsParam.getMobile());
										smsOtp.setTxn(txn);
										smsOtp.setOtp(Integer.valueOf(otp));
										smsOtp.setIp(smsParam.getIp());
										smsOtp.setSmsdate(new Date());
										smsOtp.setStatus(true);
										smsOtp.setRes(sendRes);
										smsOtpService.saveSmsOtpEntity(smsOtp);

										return OtpServiceResponse.otpServiceResponse(txn, "true", "", "")	;
									}
								}
							}

						}

					}else  if(applicationConstantConfig.SMS_GATEWAY.equalsIgnoreCase("3")) {
						sendRes=	smsOtpApiService.sendSmsPMJSELFBIS(sms, smsParam.getMobile(),smsParam.getTemplateid());

						if(!ObjectUtils.isEmpty(sendRes)) {
							if(sendRes.split("\\|")[0].equalsIgnoreCase("1701")) {

								SmsOtpEntity smsOtp= new SmsOtpEntity();
								smsOtp.setMobile(smsParam.getMobile());
								smsOtp.setTxn("PMJSELFBIS:"+txn);
								smsOtp.setOtp(Integer.valueOf(otp));
								smsOtp.setIp(smsParam.getIp());
								smsOtp.setSmsdate(new Date());
								smsOtp.setStatus(true);
								smsOtp.setRes(sendRes);

								smsOtpService.saveSmsOtpEntity(smsOtp);

								return OtpServiceResponse.otpServiceResponse(txn, "true", "", "")	;
							}
							else {
								SmsOtpEntity smsOtp= new SmsOtpEntity();
								smsOtp.setMobile(smsParam.getMobile());
								smsOtp.setTxn("PMJSELFBIS :"+txn);
								smsOtp.setOtp(Integer.valueOf(otp));
								smsOtp.setIp(smsParam.getIp());
								smsOtp.setSmsdate(new Date());
								smsOtp.setStatus(false);
								smsOtp.setRes(sendRes);

								smsOtpService.saveSmsOtpEntity(smsOtp);
							}

						}else {
							SmsOtpEntity smsOtp= new SmsOtpEntity();
							smsOtp.setMobile(smsParam.getMobile());
							smsOtp.setTxn("PMJSELFBIS :"+txn);
							smsOtp.setOtp(Integer.valueOf(otp));
							smsOtp.setIp(smsParam.getIp());
							smsOtp.setSmsdate(new Date());
							smsOtp.setStatus(false);
							smsOtp.setRes(sendRes);

							smsOtpService.saveSmsOtpEntity(smsOtp);
						}
					}		
				}
			}	
		}catch (Exception e) {

			logger.info(e.getMessage());// TODO: handle exception
			return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "false", "", "")	;
		}


		return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "false", "", "")	;
	}



}
