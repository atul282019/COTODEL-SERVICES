package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
import com.cotodel.hrms.auth.server.repository.BankMasterRepository;
import com.cotodel.hrms.auth.server.service.BankMasterService;
@Repository
public class BankMasterServiceImpl implements BankMasterService{

	@Autowired
	BankMasterRepository bankMasterRepository;
		

	@Override
	public List<ErupiBankMasterEntity> getBankMaster() {
		// TODO Auto-generated method stub
		return bankMasterRepository.findAll();
	}

	
}
