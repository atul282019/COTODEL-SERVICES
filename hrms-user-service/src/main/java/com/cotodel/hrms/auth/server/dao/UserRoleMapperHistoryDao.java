package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.UserRoleMapperHistoryEntity;

public interface UserRoleMapperHistoryDao {
	
	public UserRoleMapperHistoryEntity saveUserRoleDetails(UserRoleMapperHistoryEntity userRoleMapperHistoryEntity);
	public List<UserRoleMapperHistoryEntity> saveUserRoleList(List<UserRoleMapperHistoryEntity> userRoleMapperHistoryEntity);
	
}
