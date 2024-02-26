package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.RolePermissionsDao;
import com.cotodel.hrms.auth.server.entity.RolePermissionsMaster;
import com.cotodel.hrms.auth.server.service.RolePermissionsMasterService;

@Service
public class RolePermissionsMasterServiceImpl implements RolePermissionsMasterService {

	@Autowired
	RolePermissionsDao  rolePermissionsDao;

	@Override
	public List<RolePermissionsMaster> getRolePermissionsMaster(int employerId) {
		// TODO Auto-generated method stub
		return rolePermissionsDao.getRolePermissionsMaster(employerId);
	}
	
}
