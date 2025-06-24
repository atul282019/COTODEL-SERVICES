package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CashFreeDao;
import com.cotodel.hrms.auth.server.dto.CashFreeOrderHistory;
import com.cotodel.hrms.auth.server.entity.CashFreeOrderEntity;
import com.cotodel.hrms.auth.server.repository.CashFreeOrderRepository;
@Repository
public class CashFreeDaoImpl implements CashFreeDao{

	@Autowired
	CashFreeOrderRepository cashFreeOrderRepository;

	@Override
	public CashFreeOrderEntity saveDetails(CashFreeOrderEntity cashFreeOrderEntity) {
		
		return cashFreeOrderRepository.saveAndFlush(cashFreeOrderEntity);
	}

	@Override
	public CashFreeOrderEntity getDetails(String customerId) {
		// TODO Auto-generated method stub
		return cashFreeOrderRepository.findBycustomerId(customerId);
	}

	@Override
	public List<CashFreeOrderEntity> getDetailsOrderId(Long orgId) {
		// TODO Auto-generated method stub
		return cashFreeOrderRepository.findByOrgId(orgId);
	}

	@Override
	public List<CashFreeOrderHistory> getDetailsHistory(Long orgId) {
		// TODO Auto-generated method stub
		return cashFreeOrderRepository.findCashFreeOrderHistory(orgId);
	}

	

}
