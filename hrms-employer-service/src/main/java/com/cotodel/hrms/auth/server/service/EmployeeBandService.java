package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeBandAddTierRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeBandAddTierReviewRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeBandRequest;
import com.cotodel.hrms.auth.server.model.EmployeeBandEntity;

public interface EmployeeBandService {
	
	public EmployeeBandRequest  saveCompEmployeeBandDetails(EmployeeBandRequest request);
	//public EmployeeBandEntity  getCompEmployeeBandDetails(String bandid);
	public List<EmployeeBandEntity> getEmployeeBandList();
	
	public EmployeeBandAddTierRequest  saveEmployeeBandAddTier(EmployeeBandAddTierRequest request);
	public EmployeeBandAddTierReviewRequest  saveEmployeeBandAddTierReview(EmployeeBandAddTierReviewRequest request);
	public EmployeeBandAddTierRequest  getEmployeeBandAddTierReview(Long  employerId);
}
