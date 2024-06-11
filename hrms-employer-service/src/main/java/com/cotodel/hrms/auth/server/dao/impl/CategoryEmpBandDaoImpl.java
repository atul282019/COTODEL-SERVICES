package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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
	@Modifying
	public List<CategoryEmployeeBandEntity> saveDetails(List<CategoryEmployeeBandEntity> expenseCategoryBandEntity) {
		// TODO Auto-generated method stub
		return categoryEmpBandRepository.saveAll(expenseCategoryBandEntity);
	}



	@Override
	public List<CategoryEmployeeBandEntity> getDetails(long categoryId) {
		// TODO Auto-generated method stub
		return categoryEmpBandRepository.findByCategoryId(categoryId);
	}
	

}
