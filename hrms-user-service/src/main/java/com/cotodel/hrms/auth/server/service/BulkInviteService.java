package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.BulkInviteRequest;

public interface BulkInviteService {

	public void sendEmailToEmployee(BulkInviteRequest user);	
}
