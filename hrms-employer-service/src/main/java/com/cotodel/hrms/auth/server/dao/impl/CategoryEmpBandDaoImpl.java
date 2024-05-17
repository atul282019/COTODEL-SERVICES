package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CategoryEmpBandDao;
import com.cotodel.hrms.auth.server.model.CategoryEmployeeBandEntity;
import com.cotodel.hrms.auth.server.repository.CategoryEmpBandRepository;
@Repository
public class CategoryEmpBandDaoImpl implements CategoryEmpBandDao{

	@Autowired
	CategoryEmpBandRepository categoryEmpBandRepository;

	@Override
	public CategoryEmployeeBandEntity saveDetails(CategoryEmployeeBandEntity employeeBandEntity) {
		
		return categoryEmpBandRepository.saveAndFlush(employeeBandEntity);
	}
	


	@Override
	public List<CategoryEmployeeBandEntity> saveDetails(List<CategoryEmployeeBandEntity> expenseCategoryBandEntity) {
		// TODO Auto-generated method stub
		return categoryEmpBandRepository.saveAllAndFlush(expenseCategoryBandEntity);
	}
	

}
