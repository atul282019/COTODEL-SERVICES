package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.LinkSubMultipleAccountTempDao;
import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleTempEntity;
import com.cotodel.hrms.auth.server.repository.LinkSubMultipleAccountTempRepository;
@Repository
public class LinkSubMultipleAccountTempDaoImpl implements LinkSubMultipleAccountTempDao{

	@Autowired
	LinkSubMultipleAccountTempRepository linkSubMultipleAccountTempRepository;

	@Override
	public LinkSubAccountMultipleTempEntity saveDetails(LinkSubAccountMultipleTempEntity linkSubAccountMultipleTempEntity) {
		// TODO Auto-generated method stub
		return linkSubMultipleAccountTempRepository.saveAndFlush(linkSubAccountMultipleTempEntity);
	}

	
	@Override
	public LinkSubAccountMultipleTempEntity getDetails(Long id) {
		return linkSubMultipleAccountTempRepository.getdetailById(id);
	}


	@Override
	public List<LinkSubAccountMultipleTempEntity> getLinkMultipleDetails(Long orgId) {
		// TODO Auto-generated method stub
		return linkSubMultipleAccountTempRepository.getLinkMultipleAccount(orgId);
	}


	@Override
	public List<LinkSubAccountMultipleTempEntity> getLinkMultipleDetailsByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return linkSubMultipleAccountTempRepository.getLinkMultipleAccountByOrgId(orgId);
	}


	@Override
	public LinkSubAccountMultipleTempEntity getLinkMultipleAccountByAccNoOrgId(String acNumber, Long orgId) {
		// TODO Auto-generated method stub
		return linkSubMultipleAccountTempRepository.getLinkMultipleAccountByAccNoOrgId(acNumber, orgId);
	}



}
