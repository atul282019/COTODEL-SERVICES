package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;

public interface ErupiLinkAccountDao {
	public ErupiLinkAccountEntity saveDetails(ErupiLinkAccountEntity linkAccountErupiEntity);	
	public ErupiLinkAccountEntity findByOrgId(Long orgid);
	public List<ErupiLinkAccountEntity> findByErupiLinkOrgId(Long orgid);
	public ErupiLinkAccountEntity findByErupiLinkAccNumber(String accNo);
	public ErupiLinkAccountEntity findByErupiPrimaryAccDetails(Long orgid);
}
