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
import com.cotodel.hrms.auth.server.util.CommonUtility;

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
			boolean valid=CommonUtility.isValidEmail(string);
			if(valid)
				CommonUtility.sendEmail(userRequest);
			
		}
		
		for (String string : ContractorList) {
			UserRequest userRequest=new UserRequest();
			userRequest.setEmail(string);
			boolean valid=CommonUtility.isValidEmail(string);
			if(valid)
				CommonUtility.sendEmail(userRequest);
			
		}
		
	}
	

}
