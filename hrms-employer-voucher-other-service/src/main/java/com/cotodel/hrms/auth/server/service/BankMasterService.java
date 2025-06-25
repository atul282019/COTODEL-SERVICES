package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.BankMasterRequest;
import com.cotodel.hrms.auth.server.dto.BankMasterStatusRequest;
import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
import com.cotodel.hrms.auth.server.model.ErupiBankNameMasterEntity;

public interface BankMasterService {
	
	public List<ErupiBankMasterEntity>  getBankMaster();
	
	public BankMasterRequest  saveBankMaster(BankMasterRequest bankMasterRequest);
	
	public List<ErupiBankNameMasterEntity>  getBankNameMaster();
	public BankMasterStatusRequest updateBankMaster(BankMasterStatusRequest bankMasterStatusRequest);
	
}
