package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.LinkSubAccountMultipleTempEntity;

public interface LinkSubMultipleAccountTempDao {
	public LinkSubAccountMultipleTempEntity saveDetails(LinkSubAccountMultipleTempEntity erCreationRequestEntity);
	public LinkSubAccountMultipleTempEntity getDetails(Long id);
	public List<LinkSubAccountMultipleTempEntity> getLinkMultipleDetails(Long orgId);
	public List<LinkSubAccountMultipleTempEntity> getLinkMultipleDetailsByOrgId(Long orgId);
	public LinkSubAccountMultipleTempEntity getLinkMultipleAccountByAccNoOrgId(String acNumber,Long orgId);
}
