package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.CashFreeOrderWebHookEntity;

public interface CashFreeWebHookDao {
	public CashFreeOrderWebHookEntity saveDetails(CashFreeOrderWebHookEntity cashFreeWebHookEntity);
	public List<CashFreeOrderWebHookEntity> getDetails(String orderId);
}
