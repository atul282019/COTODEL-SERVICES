package com.cotodel.hrms.auth.server.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.dto.UserRequest;


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
	
	public static String userSmsRequest(String clientid,String secretid,String requestJson,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request Json---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("clientId", clientid);
			headers.set("clientSecret", secretid);	 

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(Exception e){
			e.printStackTrace();
			
			return e.getMessage();
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

	public static String sendEmail(UserRequest req) {
		// Set up mail server properties
				req.setMobile("9911851042");
				Properties properties = new Properties();
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.port", "587");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.starttls.enable", "true");
				Session session = Session.getInstance(properties, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("cotodel.info@gmail.com", "zxvg tryo uhdh akgf");
						//return new PasswordAuthentication("cotodel917@gmail.com", "CotoDel@123");
					}
				});

				Message msg = new MimeMessage(session);
				try {
					msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
					msg.setFrom(new InternetAddress(req.getEmail(), false));
					msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(req.getEmail()));
					msg.setSubject("Congratulations on Your Achievement");
					msg.setContent("Verify Sigin", "text/html");
					msg.setSentDate(new Date());
					byte[] bytes = req.getMobile().getBytes(StandardCharsets.UTF_8);
					String encoded = DatatypeConverter.printBase64Binary(bytes);
					byte[] byt = req.getEmail().getBytes(StandardCharsets.UTF_8);
					String emailbyt = DatatypeConverter.printBase64Binary(byt);

//					String confirmationUrl = applicationConstantConfig.emailVerifyUrl + "?token=" + encoded.replaceAll("==", "")
//							+ "/" + emailbyt;
//					String emailBody = "Click the link to verify your email: " + confirmationUrl;

//					String link = "<p></p><a href=" + applicationConstantConfig.emailVerifyUrl + "/"
//							+ emailbyt.replaceAll("==", "") + "/" + encoded.replaceAll("==", "")
//							+ "><h3>Please click here to verify....<h3></a>";
					
					String content=" <div style=\"max-width: 600px; margin: 0 auto;\">\r\n"
							+ "    <h1 style=\"color: #333333;\">Congratulations on Your Achievement!</h1>\r\n"
							+ "    <p style=\"font-size: 16px;\">Dear All,</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">I hope this email finds you well. I just wanted to take a moment to extend my heartfelt congratulations to you on your recent achievement! It's truly wonderful to see your hard work and dedication paying off.</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Your accomplishment is a testament to your perseverance, skills, and determination. I have always admired your passion for what you do, and it's no surprise that you have achieved such success.</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Please know that your efforts have not gone unnoticed, and I am genuinely thrilled for you. This is just the beginning of many great things to come, and I am confident that you will continue to excel in all your future endeavors.</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Once again, congratulations on this well-deserved achievement! Wishing you continued success and happiness.</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Warm regards,</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Team Cotodel</p>\r\n"
							+ "  </div>";
					MimeBodyPart messageBodyPart = new MimeBodyPart();
					// String password =generatePassword(8);
					messageBodyPart.setContent(content, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					msg.setContent(multipart);
					Transport.send(msg);
					logger.info("verification mail sended successfully to :" + req.getEmail());
					return req.getEmail();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				return req.getEmail();
	}
	public static boolean isValidEmail(String email)
    {       
		 String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";  
	     //Compile regular expression to get the pattern  
	     Pattern pattern = Pattern.compile(regex);  
	     Matcher matcher = pattern.matcher(email);
	     return matcher.matches();
    }
}
