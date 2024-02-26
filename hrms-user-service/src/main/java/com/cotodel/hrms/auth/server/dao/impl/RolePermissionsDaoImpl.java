package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.RolePermissionsDao;
import com.cotodel.hrms.auth.server.entity.RolePermissionsMaster;
import com.cotodel.hrms.auth.server.repository.RolePermissionsMasterRepository;

@Repository
public class RolePermissionsDaoImpl implements RolePermissionsDao{

	@Autowired
	RolePermissionsMasterRepository   rolePermissionsMasterRepository;

	@Override
	public List<RolePermissionsMaster> getRolePermissionsMaster(int employerId) {
		// TODO Auto-generated method stub
		return rolePermissionsMasterRepository.getByRolePermissionsList(employerId);
	}

	
	
	

}
