package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserRoleMasterDao;
import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;
import com.cotodel.hrms.auth.server.repository.RoleNewMasterRepository;

@Repository
public class RoleMasterDaoImpl implements UserRoleMasterDao {

	@Autowired
	RoleNewMasterRepository roleNewMasterRepository;

	@Override
	public RoleMasterEntity getUserRoleList(String roleDesc) {
		// TODO Auto-generated method stub
		return roleNewMasterRepository.getRoleMAster(roleDesc);
	}

	@Override
	public List<RoleMasterEntity> getUserRoleMaster() {
		// TODO Auto-generated method stub
		return roleNewMasterRepository.getRoleMasterList();
	}

	


	
}
