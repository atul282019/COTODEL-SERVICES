package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;

public interface ErupiLinkAccountDao {
	public ErupiLinkAccountEntity saveDetails(ErupiLinkAccountEntity linkAccountErupiEntity);	
	public ErupiLinkAccountEntity findByOrgId(Long orgid);
	
}
