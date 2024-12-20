package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;

public interface UserRoleMasterDao {
	
	public RoleMasterEntity getUserRoleList(String roleDesc);
	public List<RoleMasterEntity> getUserRoleMaster();
}
