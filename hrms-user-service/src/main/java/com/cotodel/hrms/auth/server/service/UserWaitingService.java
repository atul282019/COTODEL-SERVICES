package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;

public interface UserWaitingService {	
	public UserWaitingListEntity saveUserDetails(UserWaitingListEntity user);	
	public UserWaitingListEntity checkUserEmail(String userEmail);
	public List<UserWaitingListEntity> checkUserList();
}
