package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherTxnDao;
import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
import com.cotodel.hrms.auth.server.model.WorkFlowMasterEntity;
import com.cotodel.hrms.auth.server.repository.ErupiVoucherTxnRepository;
import com.cotodel.hrms.auth.server.repository.WorkFlowMasterRepository;
@Repository
public class ErupiVoucherTxnDaoImpl implements ErupiVoucherTxnDao{

	@Autowired
	ErupiVoucherTxnRepository erupiVoucherTxnRepository;
	
	@Autowired
	WorkFlowMasterRepository workFlowMasterRepository;

	@Override
	public ErupiVoucherTxnDetailsEntity saveDetails(ErupiVoucherTxnDetailsEntity erupiVoucherTxnEntity) {
		// TODO Auto-generated method stub
		return erupiVoucherTxnRepository.saveAndFlush(erupiVoucherTxnEntity);
	}

	@Override
	public List<ErupiVoucherTxnDetailsEntity> getVoucherTxnDetails() {
		// TODO Auto-generated method stub
		return erupiVoucherTxnRepository.findAll();
	}

	@Override
	public WorkFlowMasterEntity getWorkFlowId(Long workflowid, String type) {
		// TODO Auto-generated method stub
		return workFlowMasterRepository.findByWorkFlowId(workflowid, type);
	}

	@Override
	public List<ErupiVoucherTxnDetailsEntity> getVoucherTxnList(Long orgID) {
		// TODO Auto-generated method stub
		return erupiVoucherTxnRepository.findByOrgId(orgID);
	}

	@Override
	public ErupiVoucherTxnDetailsEntity findByDetailId(Long id, Long workflowid) {
		// TODO Auto-generated method stub
		return erupiVoucherTxnRepository.findByDetailId(id, workflowid);
	}

	


	
	
}
