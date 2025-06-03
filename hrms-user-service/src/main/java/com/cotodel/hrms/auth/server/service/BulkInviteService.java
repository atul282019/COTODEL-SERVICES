package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.BulkInviteRequest;
import com.cotodel.hrms.auth.server.dto.UserBulkUploadRequest;

public interface BulkInviteService {

	public void sendEmailToEmployee(BulkInviteRequest user);
	
	public UserBulkUploadRequest saveBulkUpload(UserBulkUploadRequest userBulkUploadRequest);
	
	public void sendEmailToEmployeeNew(BulkInviteRequest user);
}
