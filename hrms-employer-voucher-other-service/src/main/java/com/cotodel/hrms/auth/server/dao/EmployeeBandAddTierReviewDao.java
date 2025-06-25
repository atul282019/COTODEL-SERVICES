package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierReviewEntity;

public interface EmployeeBandAddTierReviewDao {	
	public List<EmployeeBandAddTierReviewEntity> saveDetails(List<EmployeeBandAddTierReviewEntity> employeeBandAddTierEntity);
	
}
