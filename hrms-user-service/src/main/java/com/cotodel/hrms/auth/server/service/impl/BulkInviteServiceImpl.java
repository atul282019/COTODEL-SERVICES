package com.cotodel.hrms.auth.server.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.BulkInviteRequest;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.BulkInviteService;

@Repository
public class BulkInviteServiceImpl implements BulkInviteService {

	private static final Logger logger = LoggerFactory.getLogger(BulkInviteServiceImpl.class);

	@Autowired
	ApplicationConstantConfig applicationConstantConfig;

	@Override
	public void sendEmailToEmployee(BulkInviteRequest req) {
		
		List<String> employeeList = Arrays.asList(req.getInviteEmployee().split(","));
		List<String> ContractorList = Arrays.asList(req.getInviteContractor().split(","));
		for (String string : employeeList) {
			UserRequest userRequest=new UserRequest();
			userRequest.setEmail(string);
			boolean valid=isValidEmail(string);
			if(valid)
				sendEmail(userRequest);
			
		}
		for (String string : ContractorList) {
			UserRequest userRequest=new UserRequest();
			userRequest.setEmail(string);
			boolean valid=isValidEmail(string);
			if(valid)
				sendEmail(userRequest);
			
		}
		
	}
	
	public String sendEmail(UserRequest req) {
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
						return new PasswordAuthentication("dkawal73@gmail.com", "jaygeajbqvinwacz");
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

					String confirmationUrl = applicationConstantConfig.emailVerifyUrl + "?token=" + encoded.replaceAll("==", "")
							+ "/" + emailbyt;
					String emailBody = "Click the link to verify your email: " + confirmationUrl;

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
							+ "    <p style=\"font-size: 16px;\">Fakhruddeen</p>\r\n"
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
