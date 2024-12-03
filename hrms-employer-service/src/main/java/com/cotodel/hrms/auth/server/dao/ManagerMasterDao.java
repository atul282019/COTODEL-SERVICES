package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ManagerLblMasterEntity;

public interface ManagerMasterDao {
	public ManagerLblMasterEntity saveDetails(ManagerLblMasterEntity managerLblMasterEntity);
	public List<ManagerLblMasterEntity> getManagerDetails(Long orgId);
}
