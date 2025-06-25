package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.TransactionHistoryDao;
import com.cotodel.hrms.auth.server.model.TransactionHistoryEntity;
import com.cotodel.hrms.auth.server.repository.TransactionHistoryRepository;
@Repository
public class TransctionHistoryDaoImpl implements TransactionHistoryDao{

	@Autowired
	TransactionHistoryRepository transactionHistoryRepository;

	@Override
	public TransactionHistoryEntity saveDetails(TransactionHistoryEntity transactionHistoryEntity) {
		// TODO Auto-generated method stub
		return transactionHistoryRepository.saveAndFlush(transactionHistoryEntity);
	}

	
}
