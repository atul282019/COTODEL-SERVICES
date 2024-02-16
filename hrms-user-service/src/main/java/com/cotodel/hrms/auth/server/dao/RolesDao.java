package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.RoleMaster;

public interface RolesDao {
	
	public List<RoleMaster>  getRolesMaster(int employerId);

}
