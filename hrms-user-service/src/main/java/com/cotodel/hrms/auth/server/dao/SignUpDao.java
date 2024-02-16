package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.entity.SignUpEntity;

public interface SignUpDao {
	
	public SignUpEntity saveUserDetails(SignUpEntity user);
}
