package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseTravelAdvanceDao;
import com.cotodel.hrms.auth.server.model.ExpanceTravelAdvanceEntity;
import com.cotodel.hrms.auth.server.repository.ExpenseCategoryBandRepository;
import com.cotodel.hrms.auth.server.repository.ExpenseTravelAdvanceRepository;
@Repository

public class ExpenseTravelAdvanceDaoImpl implements ExpenseTravelAdvanceDao{

	@Autowired
	ExpenseTravelAdvanceRepository expenseTravelAdvanceRepository;

	@Override
	public ExpanceTravelAdvanceEntity saveDetails(ExpanceTravelAdvanceEntity expanceTravelAdvanceEntity) {
		return expenseTravelAdvanceRepository.saveAndFlush(expanceTravelAdvanceEntity);
	}

	@Override
	public ExpanceTravelAdvanceEntity findByEmployerId(Long employerId) {
		
		return expenseTravelAdvanceRepository.findByEmpoyerId(employerId);
	}

	

}
