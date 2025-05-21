package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.CashFreeOrderHistory;
import com.cotodel.hrms.auth.server.entity.CashFreeOrderEntity;

public interface CashFreeDao {
	public CashFreeOrderEntity saveDetails(CashFreeOrderEntity cashFreeOrderEntity);
	public CashFreeOrderEntity getDetails(String customerId);
	public List<CashFreeOrderEntity> getDetailsOrderId(Long orgId);
	public List<CashFreeOrderHistory> getDetailsHistory(Long orgId);
}
