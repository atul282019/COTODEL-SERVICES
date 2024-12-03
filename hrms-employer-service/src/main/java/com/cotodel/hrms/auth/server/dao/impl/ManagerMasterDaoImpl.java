package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ManagerMasterDao;
import com.cotodel.hrms.auth.server.model.ManagerLblMasterEntity;
import com.cotodel.hrms.auth.server.repository.ManagerMasterRepository;
@Repository
public class ManagerMasterDaoImpl implements ManagerMasterDao{

	@Autowired
	ManagerMasterRepository managerMasterRepository;

	@Override
	public ManagerLblMasterEntity saveDetails(ManagerLblMasterEntity managerLblMasterEntity) {
		// TODO Auto-generated method stub
		return managerMasterRepository.saveAndFlush(managerLblMasterEntity);
	}

	@Override
	public List<ManagerLblMasterEntity> getManagerDetails(Long orgId) {
		// TODO Auto-generated method stub
		return managerMasterRepository.findByOrgId(orgId);
	}

	
	
	

}
