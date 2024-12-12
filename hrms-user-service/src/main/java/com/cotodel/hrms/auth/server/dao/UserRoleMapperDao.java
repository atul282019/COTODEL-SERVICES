package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperEntity;

public interface UserRoleMapperDao {
	
	public UserRoleMapperEntity saveUserRoleDetails(UserRoleMapperEntity userRoleMapper);
	
}
