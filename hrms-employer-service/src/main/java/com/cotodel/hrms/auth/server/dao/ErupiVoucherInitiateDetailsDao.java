package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationDetailsEntity;

public interface ErupiVoucherInitiateDetailsDao {
	public ErupiVoucherCreationDetailsEntity saveDetails(ErupiVoucherCreationDetailsEntity erupiVoucherInitiateDetailsEntity);	
	public int updateWorkflowId(Long id ,Long WorkflowId);		
	public List<ErupiVoucherCreatedDto> getVoucherCreationList(Long orgid);
	public ErupiVoucherCreationDetailsEntity getErupiVoucherCreationDetails(Long id);
	public List<Object []> getVoucherSummary(Long orgID);
}
