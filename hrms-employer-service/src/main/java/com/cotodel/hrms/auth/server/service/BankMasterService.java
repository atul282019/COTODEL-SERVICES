package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.BankMasterRequest;
import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;

public interface BankMasterService {
	
	public List<ErupiBankMasterEntity>  getBankMaster();
	
	public BankMasterRequest  saveBankMaster(BankMasterRequest bankMasterRequest);
}
