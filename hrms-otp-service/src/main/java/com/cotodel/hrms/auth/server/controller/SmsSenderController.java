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

import com.cotodel.hrms.auth.server.dto.SmsParam;
import com.cotodel.hrms.auth.server.model.SmsOtpEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.response.OtpServiceResponse;
import com.cotodel.hrms.auth.server.service.SmsOtpService;
import com.cotodel.hrms.auth.server.sms.service.SmsOtpApiService2;
import com.cotodel.hrms.auth.server.sms.service.TemplateService;
import com.cotodel.hrms.auth.server.util.GenerateOtpFunction;
import com.cotodel.hrms.auth.server.util.StrListFunction;
import com.cotodel.hrms.auth.server.util.TransactionManager;

@RestController
@RequestMapping("/bis")
@CrossOrigin
public class SmsSenderController {


	private static final Logger logger = LoggerFactory.getLogger(SmsSenderController.class);


	@Autowired
	public SmsOtpApiService2 smsOtpApiService;

	@Autowired
	public SmsOtpService smsOtpService;

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	public TemplateService templateService;

	@PostMapping(value="/sms/sender/2.0", produces=MediaType.APPLICATION_JSON_VALUE)
	public String smsSenderRequest(@RequestBody SmsParam smsParam) {
		
		String sendRes= null;
		String sms=null;
		try {
			String txn=TransactionManager.getTransactionId();
			logger.info(txn);
			logger.info("mobile :"+smsParam.getMobile());

			String	otp =GenerateOtpFunction.generateOTP();

			if(!ObjectUtils.isEmpty(smsParam)) {

				if(StrListFunction.convertArrayToList(applicationConstantConfig.BIS_SMS_GATEWAY_SMS_LIST.split(",")).contains(smsParam.getTemplateid())) {

				 if(smsParam.getTemplateid().equalsIgnoreCase("1007163593307833597") || smsParam.getTemplateid().equalsIgnoreCase("1007163593317209694")) {
					 sms=templateService.getTemplateSms(smsParam.getTemplateid()).
							replace("{#var1#}", smsParam.getName()).replace("{#var2#}", smsParam.getRefId());
				 }else if(smsParam.getTemplateid().equalsIgnoreCase("1007166013592993070")) {
					 
					 sms=templateService.getTemplateSms(smsParam.getTemplateid()).
								replace("{#var1#}", smsParam.getUuid()).replace("{#var2#}", smsParam.getOrg_name())
								.replace("{#var3#}", smsParam.getAmount()).replace("{#var4#}", smsParam.getExpdate())
								.replace("{#var5#}", smsParam.getLink()).replace("{#var6#}", smsParam.getBank())
								.replace("{#var7#}", smsParam.getTollNumber());
					 
				 }else if(smsParam.getTemplateid().equalsIgnoreCase("1007166013575944759") ) {
					 
					 sms=templateService.getTemplateSms(smsParam.getTemplateid()).
								replace("{#var1#}", smsParam.getRefId()).replace("{#var2#}", smsParam.getLink())
								.replace("{#var3#}", "");
					 
				 }else if(smsParam.getTemplateid().equalsIgnoreCase("1007166013579066985")) {
					 
					 sms=templateService.getTemplateSms(smsParam.getTemplateid()).
								replace("{#var1#}", smsParam.getRefId());
				 }else if(smsParam.getTemplateid().equalsIgnoreCase("1007166255090158204")) {
					 
					 sms=templateService.getTemplateSms(smsParam.getTemplateid()).
								replace("var1", smsParam.getUuid()).replace("var2", smsParam.getPurpose())
								.replace("var3", smsParam.getOrg_name()).replace("var4", smsParam.getAmount())
								.replace("var5", smsParam.getExpdate()).replace("var6", smsParam.getUsage())
								.replace("var7", smsParam.getName())
								.replace("var8", smsParam.getMisc())
								.replace("var9", smsParam.getLink())
								.replace("varA1", smsParam.getTollNumber());
					 
				 }else if(smsParam.getTemplateid().equalsIgnoreCase("1007164982263345893") ) {
					 sms=templateService.getTemplateSms(smsParam.getTemplateid()).
								replace("var1", smsParam.getName()).replace("var2", smsParam.getRefId());
				 }else if(smsParam.getTemplateid().equalsIgnoreCase("1007168051684626090")) {
					 
					 sms=templateService.getTemplateSms(smsParam.getTemplateid()).
								replace("{#var1#}", smsParam.getRefId());
				 }else if(smsParam.getTemplateid().equalsIgnoreCase("1007168171513942130")) {
					 
					 sms=templateService.getTemplateSms(smsParam.getTemplateid()).
								replace("{#var1#}", smsParam.getRefId());
				 }
				 
					if(applicationConstantConfig.SMS_GATEWAY.equalsIgnoreCase("1")) {
					
					 sendRes=	smsOtpApiService.sendSms(sms, smsParam.getMobile(),smsParam.getTemplateid());

					if(!ObjectUtils.isEmpty(sendRes)) {
						JSONObject resJson=new JSONObject(sendRes);
						if(!ObjectUtils.isEmpty(resJson)) {

							if(resJson.getString("STATUS").equalsIgnoreCase("OK")) {

								JSONObject response=resJson.getJSONObject("RESPONSE");
								if(!ObjectUtils.isEmpty(response)) {
									if(response.getString("INFO").equalsIgnoreCase("SUBMITTED")) {
										SmsOtpEntity smsOtp= new SmsOtpEntity();
										smsOtp.setMobile(smsParam.getMobile());
										smsOtp.setTxn("SMS_"+txn);
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

				}else {
					return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "false", "", "")	;	
				}
			}else {


				return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "false", "", "")	;

			}

		}catch (Exception e) {

			logger.info(e.getMessage());// TODO: handle exception
			return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "false", "", "")	;
		}


		return OtpServiceResponse.otpServiceResponse(TransactionManager.getTransactionId(), "false", "", "")	;
	}





}
