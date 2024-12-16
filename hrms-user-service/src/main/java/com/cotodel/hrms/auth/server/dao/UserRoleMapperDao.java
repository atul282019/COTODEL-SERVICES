package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.UserRoleMapperDto;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperEntity;

public interface UserRoleMapperDao {
	
	public UserRoleMapperEntity saveUserRoleDetails(UserRoleMapperEntity userRoleMapper);
	public List<UserRoleMapperDto> getUserRoleList(String mobile);
	public UserRoleMapperEntity getUserRoleMapper(String mobile,Long orgId,int roleId);
	public void deleteUserRoleMapper(Long id);
	public UserRoleMapperEntity getUserRoleMapperById(Long id);
}
