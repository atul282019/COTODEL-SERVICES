package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.PermissionsMaster;

public interface PermissionsMasterService {
	
	public List<PermissionsMaster>  getPermissionsMaster(int employerId);


}
