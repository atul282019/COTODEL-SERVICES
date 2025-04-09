package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CashFreeWebHookDao;
import com.cotodel.hrms.auth.server.entity.CashFreeOrderWebHookEntity;
import com.cotodel.hrms.auth.server.entity.CashFreeWebHookLogEntity;
import com.cotodel.hrms.auth.server.repository.CashFreeOrderWebHookRepository;
import com.cotodel.hrms.auth.server.repository.CashFreeWebHookLogRepository;
@Repository
public class CashFreeWebHookDaoImpl implements CashFreeWebHookDao{

	@Autowired
	CashFreeOrderWebHookRepository cashFreeOrderWebHookRepository;

	@Override
	public CashFreeOrderWebHookEntity saveDetails(CashFreeOrderWebHookEntity cashFreeWebHookEntity) {
		
		return cashFreeOrderWebHookRepository.saveAndFlush(cashFreeWebHookEntity);
	}

	@Override
	public List<CashFreeOrderWebHookEntity> getDetails(String orderId) {
		// TODO Auto-generated method stub
		return cashFreeOrderWebHookRepository.findByOrderId(orderId);
	}

}
