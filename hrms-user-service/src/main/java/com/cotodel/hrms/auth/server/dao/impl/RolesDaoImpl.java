package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.RolesDao;
import com.cotodel.hrms.auth.server.entity.RoleMaster;
import com.cotodel.hrms.auth.server.repository.RolesMasterRepository;

@Repository
public class RolesDaoImpl implements RolesDao{

	@Autowired
	RolesMasterRepository   rolesMasterRepository;
	
	@Override
	public List<RoleMaster> getRolesMaster(int employerId) {
		// TODO Auto-generated method stub
		return rolesMasterRepository.getByEmployerList(Integer.valueOf(employerId));
	}

}
