package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.OrganizationDao;
import com.cotodel.hrms.auth.server.entity.OrganizationMaster;
import com.cotodel.hrms.auth.server.repository.OrganizationRepository;

@Repository
public class OrganizationDaoImpl implements OrganizationDao{

	@Autowired
	OrganizationRepository   oraOrganizationRepository;
	
	@Override
	public List<OrganizationMaster> getOrganizationMaster() {
		return oraOrganizationRepository.getAllList();
	}

}
