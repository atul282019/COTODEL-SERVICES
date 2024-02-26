package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.RolePermissionsMaster;

public interface RolePermissionsDao {
	
	public List<RolePermissionsMaster>  getRolePermissionsMaster(int employerId);

}
