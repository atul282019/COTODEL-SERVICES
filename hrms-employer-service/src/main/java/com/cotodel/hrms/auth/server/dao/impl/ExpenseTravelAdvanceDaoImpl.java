package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseTravelAdvanceDao;
import com.cotodel.hrms.auth.server.model.AdvanceRequestSettingEntity;
import com.cotodel.hrms.auth.server.repository.ExpenseTravelAdvanceRepository;
@Repository

public class ExpenseTravelAdvanceDaoImpl implements ExpenseTravelAdvanceDao{

	@Autowired
	ExpenseTravelAdvanceRepository expenseTravelAdvanceRepository;

	@Override
	public AdvanceRequestSettingEntity saveDetails(AdvanceRequestSettingEntity advanceRequestSettingEntity) {
		return expenseTravelAdvanceRepository.saveAndFlush(advanceRequestSettingEntity);
	}

	@Override
	public AdvanceRequestSettingEntity findByEmployerId(Long employerId) {
		
		return expenseTravelAdvanceRepository.findByEmpoyerId(employerId);
	}

	

}
