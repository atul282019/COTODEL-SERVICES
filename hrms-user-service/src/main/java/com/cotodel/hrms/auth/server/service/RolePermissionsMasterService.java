package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.RolePermissionsMaster;

public interface RolePermissionsMasterService {
	
	public List<RolePermissionsMaster>  getRolePermissionsMaster(int employerId);


}
