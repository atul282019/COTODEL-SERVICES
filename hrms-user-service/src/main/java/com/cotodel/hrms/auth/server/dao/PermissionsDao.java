package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.PermissionsMaster;

public interface PermissionsDao {
	
	public List<PermissionsMaster>  getPermissionsMaster(int employerId);

}
