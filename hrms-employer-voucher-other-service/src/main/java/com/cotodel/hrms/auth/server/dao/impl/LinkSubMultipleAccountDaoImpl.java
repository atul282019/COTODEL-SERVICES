package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.LinkSubMultipleAccountDao;
import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleEntity;
import com.cotodel.hrms.auth.server.repository.LinkSubMultipleAccountRepository;
@Repository
public class LinkSubMultipleAccountDaoImpl implements LinkSubMultipleAccountDao{

	@Autowired
	LinkSubMultipleAccountRepository linkSubMultipleAccountRepository;

	@Override
	public LinkSubAccountMultipleEntity saveDetails(LinkSubAccountMultipleEntity linkSubAccountMultipleEntity) {
		// TODO Auto-generated method stub
		return linkSubMultipleAccountRepository.saveAndFlush(linkSubAccountMultipleEntity);
	}

	
	@Override
	public LinkSubAccountMultipleEntity getDetails(Long id) {
		return linkSubMultipleAccountRepository.getById(id);
	}


	@Override
	public int updateBalance(Float balance, Long id) {
		return linkSubMultipleAccountRepository.updateBalance(balance, id);
	}


	@Override
	public List<LinkSubAccountMultipleEntity> getLinkMultipleDetails() {
		// TODO Auto-generated method stub
		return linkSubMultipleAccountRepository.getLinkMultipleAccount();
	}


	@Override
	public List<LinkSubAccountMultipleEntity> getLinkMultipleDetailsByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return linkSubMultipleAccountRepository.getLinkMultipleAccountByOrgId(orgId);
	}


	@Override
	public LinkSubAccountMultipleEntity getLinkMultipleAccountByAccNoOrgId(String acNumber, Long orgId) {
		// TODO Auto-generated method stub
		LinkSubAccountMultipleEntity linkSubAccountMultipleEntity=null;
		try {
			linkSubAccountMultipleEntity=linkSubMultipleAccountRepository.getLinkMultipleAccountByAccNoOrgId(acNumber, orgId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return linkSubAccountMultipleEntity;
	}


	@Override
	public LinkSubAccountMultipleEntity getLinkMultipleDetailsByOrgIdWithOne(Long orgId) {
		// TODO Auto-generated method stub
		return linkSubMultipleAccountRepository.getLinkMultipleAccountByOrgIdWithone(orgId);
	}

	

}
