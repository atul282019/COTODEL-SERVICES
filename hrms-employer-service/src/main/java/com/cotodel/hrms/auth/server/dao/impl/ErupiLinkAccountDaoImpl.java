package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cotodel.hrms.auth.server.dao.ErupiLinkAccountDao;
import com.cotodel.hrms.auth.server.model.LinkAccountErupiEntity;
import com.cotodel.hrms.auth.server.repository.ErupiLinkAccountRepository;
@Repository
public class ErupiLinkAccountDaoImpl implements ErupiLinkAccountDao{

	@Autowired
	ErupiLinkAccountRepository erupiLinkAccountRepository;

	@Override
	public LinkAccountErupiEntity saveDetails(LinkAccountErupiEntity linkAccountErupiEntity) {
		// TODO Auto-generated method stub
		return erupiLinkAccountRepository.saveAndFlush(linkAccountErupiEntity);
	}


	
	
}
