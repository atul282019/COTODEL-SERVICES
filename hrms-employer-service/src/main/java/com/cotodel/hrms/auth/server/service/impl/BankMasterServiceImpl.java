package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.BankMasterDao;
import com.cotodel.hrms.auth.server.dto.BankMasterRequest;
import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
import com.cotodel.hrms.auth.server.model.ErupiVoucherTxnDetailsEntity;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;
import com.cotodel.hrms.auth.server.repository.BankMasterRepository;
import com.cotodel.hrms.auth.server.service.BankMasterService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class BankMasterServiceImpl implements BankMasterService{

	@Autowired
	BankMasterRepository bankMasterRepository;
	
	@Autowired
	BankMasterDao bankMasterDao;
		

	@Override
	public List<ErupiBankMasterEntity> getBankMaster() {
		// TODO Auto-generated method stub
		return bankMasterRepository.findAll();
	}


	@Override
	public BankMasterRequest saveBankMaster(BankMasterRequest bankMasterRequest) {

		String response="";
		ErupiBankMasterEntity erupiBankMasterEntity=null;
		try {
			response=MessageConstant.RESPONSE_FAILED;
				
			erupiBankMasterEntity=new ErupiBankMasterEntity();
			CopyUtility.copyProperties(bankMasterRequest,erupiBankMasterEntity);	
			LocalDateTime eventDate = LocalDateTime.now();	
			erupiBankMasterEntity.setCreationDate(eventDate);
			erupiBankMasterEntity=bankMasterDao.saveDetails(erupiBankMasterEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			bankMasterRequest.setResponse(response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bankMasterRequest;
	}

	
}
