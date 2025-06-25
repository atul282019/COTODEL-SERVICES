package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.EmployeeBandAddTierRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeBandNameRequest;

public interface EmployeeBandService {
	
	//public EmployeeBandRequest  saveCompEmployeeBandDetails(EmployeeBandRequest request);
	//public EmployeeBandEntity  getCompEmployeeBandDetails(String bandid);
	//public List<EmployeeBandEntity> getEmployeeBandList();
	
	public EmployeeBandAddTierRequest  saveEmployeeBandAddTier(EmployeeBandAddTierRequest request);
	//public EmployeeBandAddTierReviewRequest  saveEmployeeBandAddTierReview(EmployeeBandAddTierReviewRequest request);
	public EmployeeBandAddTierRequest  getEmployeeBandAddTierReview(Long  employerId);
	//public EmployeeBandAddTierRequest  getEmployeeBandAddTierDisable(Long  employerId);
	//public EmployeeBandAddTierRequest  getEmployeeBandAddTierEnaable(Long  employerId);
	public EmployeeBandNameRequest  getEmployeeBandName(Long  employerId);
}
