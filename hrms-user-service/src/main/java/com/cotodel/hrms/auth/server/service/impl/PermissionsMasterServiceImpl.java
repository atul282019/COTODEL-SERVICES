package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.PermissionsDao;
import com.cotodel.hrms.auth.server.entity.PermissionsMaster;
import com.cotodel.hrms.auth.server.service.PermissionsMasterService;

@Service
public class PermissionsMasterServiceImpl implements PermissionsMasterService {

	@Autowired
	PermissionsDao  permissionsDao;

//	@Override
//	public List<PermissionsMaster> getPermissionsMaster(int employerId) {
//		// TODO Auto-generated method stub
//		return permissionsDao.getPermissionsMaster(employerId);
//	}
	
	
}
