package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseCategoryMasterDao;
import com.cotodel.hrms.auth.server.model.ExpenseCategoryMasterEntity;
import com.cotodel.hrms.auth.server.repository.ExpenseCategoryMasterRepository;
@Repository
public class ExpenseCategoryMasterDaoImpl implements ExpenseCategoryMasterDao{

	@Autowired
	ExpenseCategoryMasterRepository expenseCategoryMasterRepository;

	@Override
	public List<ExpenseCategoryMasterEntity> getExpenseCategoryMaster() {
		// TODO Auto-generated method stub
		return expenseCategoryMasterRepository.getExpenseCategoryMaster();
	}

	


}
