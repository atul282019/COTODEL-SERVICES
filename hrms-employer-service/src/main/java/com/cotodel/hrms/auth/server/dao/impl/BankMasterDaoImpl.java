package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.BandDao;
import com.cotodel.hrms.auth.server.dao.BankMasterDao;
import com.cotodel.hrms.auth.server.model.BandEntity;
import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
import com.cotodel.hrms.auth.server.repository.BandRepository;
import com.cotodel.hrms.auth.server.repository.BankMasterRepository;
@Repository
public class BankMasterDaoImpl implements BankMasterDao{

	@Autowired
	BankMasterRepository bankMasterRepository;

	
	
	@Override
	public ErupiBankMasterEntity saveDetails(ErupiBankMasterEntity erupiBankMasterEntity) {
		// TODO Auto-generated method stub
		return bankMasterRepository.saveAndFlush(erupiBankMasterEntity);
	}


}
