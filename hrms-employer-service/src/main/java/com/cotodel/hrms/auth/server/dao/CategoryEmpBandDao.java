package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.CategoryEmployeeBandEntity;

public interface CategoryEmpBandDao {
	public CategoryEmployeeBandEntity saveDetails(CategoryEmployeeBandEntity expenseCategoryBandEntity);
	public List<CategoryEmployeeBandEntity> saveDetails(List<CategoryEmployeeBandEntity> expenseCategoryBandEntity);
	public List<CategoryEmployeeBandEntity> getDetails(long categoryId);
}
