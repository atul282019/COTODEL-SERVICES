package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.entity.SignUpEntity;

public interface SignUpDao {
	
	public SignUpEntity saveUserDetails(SignUpEntity user);
	List<UserDto> getUser(int employerID);
	
}
