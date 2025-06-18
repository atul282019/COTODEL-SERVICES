package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
import com.cotodel.hrms.auth.server.model.WorkFlowMasterEntity;

public interface ErupiVoucherTxnDao {
	public ErupiVoucherTxnDetailsEntity saveDetails(ErupiVoucherTxnDetailsEntity erupiVoucherTxnEntity);
	public List<ErupiVoucherTxnDetailsEntity> getVoucherTxnDetails();
	public WorkFlowMasterEntity getWorkFlowId(Long workflowid,String type);
	public List<ErupiVoucherTxnDetailsEntity> getVoucherTxnList(Long orgID);
	public ErupiVoucherTxnDetailsEntity findByDetailId(Long id,Long workflowid);
	public List<ErupiVoucherTxnDetailsEntity> findByDetailIdWithRedeem(Long id);
	
}
