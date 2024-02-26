package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.PermissionsDao;
import com.cotodel.hrms.auth.server.entity.PermissionsMaster;
import com.cotodel.hrms.auth.server.repository.PermissionsMasterRepository;

@Repository
public class PermissionsDaoImpl implements PermissionsDao{

	@Autowired
	PermissionsMasterRepository   permissionsMasterRepository;

	@Override
	public List<PermissionsMaster> getPermissionsMaster(int employerId) {
		// TODO Auto-generated method stub
		return permissionsMasterRepository.getByPermissionsList(employerId);
	}
	
	

}
