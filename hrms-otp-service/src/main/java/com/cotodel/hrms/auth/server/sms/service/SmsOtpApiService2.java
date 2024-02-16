package com.cotodel.hrms.auth.server.sms.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.util.NhaSSSLUtil;

@Service
public class SmsOtpApiService2 {


	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	private static final Logger logger = LoggerFactory.getLogger(SmsOtpApiService2.class);

	public  String sendSms(String massage,String mobile,String templateid) {
		String smsResponse="";
		StringBuilder sb = new StringBuilder();
		try {
				NhaSSSLUtil.setDefaultSSL();
			
			String requestUrl  = applicationConstantConfig.BIS_SMS_GATEWAY_URL  +
					"?user=" + applicationConstantConfig.BIS_SMS_GATEWAY_USER +
					"&authkey=" + applicationConstantConfig.BIS_SMS_GATEWAY_AUTH_KEY+
					"&sender=" + applicationConstantConfig.BIS_SMS_GATEWAY_SENDER_ID +
					"&mobile=" + mobile+
					"&text=" + massage+
					"&templateid=" + templateid +
					"&entityid=" + applicationConstantConfig.BIS_SMS_GATEWAY_ENTITY_ID;

			logger.info(requestUrl);
			URL url = new URL(requestUrl);
			HttpsURLConnection connect = (HttpsURLConnection)url.openConnection();

			smsResponse=connect.getResponseMessage();
			logger.info("connect.getResponseMessage()==="+connect.getResponseMessage());
			logger.info("connect.getContentEncoding()=="+connect.getContentEncoding());
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader((connect.getInputStream())));

			

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			logger.info(sb.toString());
			
			connect.disconnect();

		} catch(Exception ex) {
			logger.info(ex.getMessage());

		}
		return sb.toString();
	}

	
	public  String sendSmsPMJSELFBIS(String massage,String mobile,String templateid) {
		String smsResponse="";
		StringBuilder sb = new StringBuilder();
		try {
				NhaSSSLUtil.setDefaultSSL();
			
			String requestUrl  = applicationConstantConfig.BIS_SMS_GATEWAY_2_URL  +
					"?username=" + applicationConstantConfig.BIS_SMS_GATEWAY_2_USER +
					"&password=" + applicationConstantConfig.BIS_SMS_GATEWAY_2_AUTH_KEY+
					"&type=" + applicationConstantConfig.BIS_SMS_GATEWAY_2_TYPE+
					"&dlr=" + applicationConstantConfig.BIS_SMS_GATEWAY_2_DLR+
					"&destination=" + mobile+
					"&source=" + applicationConstantConfig.BIS_SMS_GATEWAY_2_SENDER_ID +
					"&message=" + massage+
					"&entityid=" + applicationConstantConfig.BIS_SMS_GATEWAY_ENTITY_ID +
					"&tempid=" + templateid ;

			logger.info(requestUrl);
			URL url = new URL(requestUrl);
			HttpsURLConnection connect = (HttpsURLConnection)url.openConnection();
			connect.setRequestMethod("POST");

			smsResponse=connect.getResponseMessage();
			logger.info("connect.getResponseCode()==="+connect.getResponseCode());
			logger.info("connect.getResponseMessage()==="+connect.getResponseMessage());
			logger.info("connect.getContentEncoding()=="+connect.getContentEncoding());

			BufferedReader br = new BufferedReader(new InputStreamReader((connect.getInputStream())));

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			logger.info(sb.toString());
			
			connect.disconnect();

		} catch(Exception ex) {
			ex.printStackTrace();
			logger.info(ex.getMessage());

		}
		return sb.toString();
	}


}
