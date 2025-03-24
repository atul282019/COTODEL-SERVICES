package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.LinkSubAccountMultipleEntity;

public interface LinkSubMultipleAccountDao {
	public LinkSubAccountMultipleEntity saveDetails(LinkSubAccountMultipleEntity erCreationRequestEntity);
	public LinkSubAccountMultipleEntity getDetails(Long id);
	public int updateBalance(Float balance,Long id);
	public List<LinkSubAccountMultipleEntity> getLinkMultipleDetails();
	public List<LinkSubAccountMultipleEntity> getLinkMultipleDetailsByOrgId(Long orgId);
	public LinkSubAccountMultipleEntity getLinkMultipleAccountByAccNoOrgId(String acNumber,Long orgId);
	public LinkSubAccountMultipleEntity getLinkMultipleDetailsByOrgIdWithOne(Long orgId);
	
}
