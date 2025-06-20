package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.WorkflowMasterDao;
import com.cotodel.hrms.auth.server.model.WorkFlowMasterEntity;
import com.cotodel.hrms.auth.server.repository.WorkFlowMasterRepository;
@Repository
public class WorkflowMasterDaoImpl implements WorkflowMasterDao{

	@Autowired
	WorkFlowMasterRepository workFlowMasterRepository;

	@Override
	public WorkFlowMasterEntity getDetails(Long id) {
		// TODO Auto-generated method stub
		return workFlowMasterRepository.findByWorkFlowId(id);
	}

	


}
