package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserBulkUploadDao;
import com.cotodel.hrms.auth.server.dto.BulkInviteRequest;
import com.cotodel.hrms.auth.server.dto.UserBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.UserRequest;
import com.cotodel.hrms.auth.server.entity.UserBulkUploadEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.BulkInviteService;
import com.cotodel.hrms.auth.server.util.CommonUtility;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@Repository
public class BulkInviteServiceImpl implements BulkInviteService {

	private static final Logger logger = LoggerFactory.getLogger(BulkInviteServiceImpl.class);

	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	UserBulkUploadDao userBulkUploadDao;

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

	@Override
	public UserBulkUploadRequest saveBulkUpload(UserBulkUploadRequest request) {
		
		String response="";
		
		UserBulkUploadEntity userBulkUploadEntity = new UserBulkUploadEntity();
		try {
			response = MessageConstant.RESPONSE_FAILED;
			Long orgId = request.getOrgId();
			String filename = request.getFileName();
			String extn = CommonUtility.getFileExtension(filename);
			String fileNameWithout = filename.substring(0, filename.lastIndexOf("."));
			String uniqueFileName = CommonUtility.generateUniqueFileName(fileNameWithout, request.getOrgId(), extn);
			LocalDate eventDate = LocalDate.now();
			CopyUtility.copyProperties(request, userBulkUploadEntity);
			userBulkUploadEntity.setCreationDate(eventDate);
			userBulkUploadEntity.setFile(request.getFile());
			userBulkUploadEntity.setFileName(uniqueFileName);
			userBulkUploadEntity.setOrgId(orgId);
			userBulkUploadEntity = userBulkUploadDao.saveUserDetails(userBulkUploadEntity);
			response = MessageConstant.RESPONSE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return request;
	}
	

}
