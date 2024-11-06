package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cotodel.hrms.auth.server.dao.ErupiLinkAccountDao;
import com.cotodel.hrms.auth.server.dto.ErupiLinkAccountWithOutResponse;
import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
import com.cotodel.hrms.auth.server.repository.ErupiLinkAccountRepository;
@Repository
public class ErupiLinkAccountDaoImpl implements ErupiLinkAccountDao{

	@Autowired
	ErupiLinkAccountRepository erupiLinkAccountRepository;

	@Override
	public ErupiLinkAccountEntity saveDetails(ErupiLinkAccountEntity linkAccountErupiEntity) {
		// TODO Auto-generated method stub
		return erupiLinkAccountRepository.saveAndFlush(linkAccountErupiEntity);
	}

	@Override
	public ErupiLinkAccountEntity findByOrgId(Long orgid) {
		// TODO Auto-generated method stub
		return erupiLinkAccountRepository.findByOrgId(orgid);
	}


	
	
}
