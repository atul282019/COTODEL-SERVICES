package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.RoleMaster;

public interface RolesMasterService {
	
	public List<RoleMaster>  getRolesMaster(int employerId);


}
