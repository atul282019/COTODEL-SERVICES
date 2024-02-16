package com.cotodel.hrms.auth.server.sms.service;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.util.NhaSSSLUtil;

@Service
public class SmsOtpApiService {

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	private static final Logger logger = LoggerFactory.getLogger(SmsOtpApiService.class);

	public String sendSmsOtp(String otp,String mobile) {

		return sendSms(applicationConstantConfig.nha_bis_sms_template.replace("{#var#}",otp),mobile);
	}

	public  String sendSms(String massage,String mobile) {
		String smsResponse="";
		try {
				NhaSSSLUtil.setDefaultSSL();
			
			String requestUrl  = applicationConstantConfig.nha_bis_sms_url  +
					"?username=" + UriUtils.encode(applicationConstantConfig.nha_bis_sms_userName, StandardCharsets.UTF_8) +
					"&pin=" + UriUtils.encode(applicationConstantConfig.nha_bis_sms_password, StandardCharsets.UTF_8) +
					"&message=" + massage+
					"&mnumber=" + UriUtils.encode(mobile, StandardCharsets.UTF_8) +
					"&signature=" + UriUtils.encode(applicationConstantConfig.nha_bis_sender_id, StandardCharsets.UTF_8) +
					"&dlt_entity_id=" + UriUtils.encode(applicationConstantConfig.nah_bis_sms_dlt_entity_id, StandardCharsets.UTF_8) +
					"&dlt_template_id=" + UriUtils.encode(applicationConstantConfig.nha_bis_sms_dlt_template_id, StandardCharsets.UTF_8) ;

			logger.info(requestUrl);
			URL url = new URL(requestUrl);
			HttpsURLConnection connect = (HttpsURLConnection)url.openConnection();

			smsResponse=connect.getResponseMessage();
			logger.info("connect.getResponseMessage()==="+connect.getResponseMessage());
			logger.info("connect.getContentEncoding()=="+connect.getContentEncoding());
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader((connect.getInputStream())));

			StringBuilder sb = new StringBuilder();

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			logger.info(sb.toString());
			
			connect.disconnect();

		} catch(Exception ex) {
			logger.info(ex.getMessage());

		}
		return smsResponse;
	}

}
