package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.UserWaitingListRequest;
import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;

public interface UserWaitingService {	
	public UserWaitingListRequest saveUserDetails(UserWaitingListRequest user);	
	public UserWaitingListEntity checkUserEmail(String userEmail);
	public List<UserWaitingListEntity> checkUserList();
}
