package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;

public interface UserWaitingService {	
	public UserWaitingListEntity saveUserDetails(UserWaitingListEntity user);	
	public UserWaitingListEntity checkUserEmail(String userEmail);
}
