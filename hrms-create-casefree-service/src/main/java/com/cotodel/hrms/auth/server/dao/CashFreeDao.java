package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.entity.CashFreeOrderEntity;

public interface CashFreeDao {
	public CashFreeOrderEntity saveDetails(CashFreeOrderEntity cashFreeOrderEntity);
	public CashFreeOrderEntity getDetails(String customerId);
}
