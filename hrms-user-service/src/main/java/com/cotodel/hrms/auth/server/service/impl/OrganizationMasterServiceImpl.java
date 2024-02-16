package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cotodel.hrms.auth.server.dao.OrganizationDao;
import com.cotodel.hrms.auth.server.entity.OrganizationMaster;
import com.cotodel.hrms.auth.server.service.OrganizationMasterService;

@Service
public class OrganizationMasterServiceImpl implements OrganizationMasterService {

	@Autowired
	OrganizationDao  organizationDao;
	
	@Override
	public List<OrganizationMaster> getOrganizationMaster() {
		return organizationDao.getOrganizationMaster();
	}

}
