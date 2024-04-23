package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;

public interface UserWaitingListDao {
	
	public UserWaitingListEntity saveUserDetails(UserWaitingListEntity user);
	UserWaitingListEntity getUser(String email);
	List<UserWaitingListEntity> getUserList();
}
