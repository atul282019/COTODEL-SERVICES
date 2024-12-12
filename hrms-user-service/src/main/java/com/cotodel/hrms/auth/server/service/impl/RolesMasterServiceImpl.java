package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.RolesDao;
import com.cotodel.hrms.auth.server.entity.RoleMaster;
import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;
import com.cotodel.hrms.auth.server.service.RolesMasterService;

@Service
public class RolesMasterServiceImpl implements RolesMasterService {

	@Autowired
	RolesDao  rolesDao;
	
	@Override
	public List<RoleMaster> getRolesMaster(int employerId) {
		// TODO Auto-generated method stub
		return rolesDao.getRolesMaster(employerId);
	}

	@Override
	public List<RoleMasterEntity> getRoleMaster() {
		// TODO Auto-generated method stub
		return rolesDao.getRolesMaster();
	}

}
