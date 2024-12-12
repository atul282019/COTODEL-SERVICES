package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.RoleMaster;
import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;

public interface RolesMasterService {
	
	public List<RoleMaster>  getRolesMaster(int employerId);

	public List<RoleMasterEntity>  getRoleMaster();
}
