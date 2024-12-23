package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.entity.UserBulkUploadEntity;

public interface UserBulkUploadDao {
	
	public UserBulkUploadEntity saveUserDetails(UserBulkUploadEntity user);
	
}
