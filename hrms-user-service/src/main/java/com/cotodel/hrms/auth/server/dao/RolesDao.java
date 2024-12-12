package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.RoleMaster;
import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;

public interface RolesDao {
	
	public List<RoleMaster>  getRolesMaster(int employerId);
	
	public List<RoleMasterEntity>  getRolesMaster();

}
