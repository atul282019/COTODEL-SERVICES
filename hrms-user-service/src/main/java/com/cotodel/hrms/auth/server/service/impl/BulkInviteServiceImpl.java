package com.cotodel.hrms.auth.server.service.impl;

import java.util.Arrays;
import java.util.List;

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
