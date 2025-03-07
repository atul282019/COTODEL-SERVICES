package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.TransactionHistoryEntity;

public interface TransactionHistoryDao {
	public TransactionHistoryEntity saveDetails(TransactionHistoryEntity transactionHistoryEntity);
//	public LinkSubAccountMultipleEntity getDetails(Long id);
//	public int updateBalance(Float balance,Long id);
}
