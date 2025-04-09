package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CashFreeWebHookLogDao;
import com.cotodel.hrms.auth.server.entity.CashFreeWebHookLogEntity;
import com.cotodel.hrms.auth.server.repository.CashFreeWebHookLogRepository;
@Repository
public class CashFreeWebHookLogDaoImpl implements CashFreeWebHookLogDao{

	@Autowired
	CashFreeWebHookLogRepository cashFreeWebHookLogRepository;

	@Override
	public CashFreeWebHookLogEntity saveDetails(CashFreeWebHookLogEntity cashFreeWebHookEntity) {
		
		return cashFreeWebHookLogRepository.saveAndFlush(cashFreeWebHookEntity);
	}

}
