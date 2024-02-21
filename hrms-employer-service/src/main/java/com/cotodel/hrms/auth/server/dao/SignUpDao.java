package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.SignUpEntity;

public interface SignUpDao {
	
	public SignUpEntity saveUserDetails(SignUpEntity user);
	public SignUpEntity getUser(Long signupId);
}
