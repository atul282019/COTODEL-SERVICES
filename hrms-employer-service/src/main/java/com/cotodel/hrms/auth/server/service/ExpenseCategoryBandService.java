package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandRequest;
import com.cotodel.hrms.auth.server.model.ExpenseBandNumberEntity;
import com.cotodel.hrms.auth.server.model.ExpenseCategoryBandEntity;

public interface ExpenseCategoryBandService {
	
	public ExpenseCategoryBandRequest  saveExpenseCategoryBandDetails(ExpenseCategoryBandRequest request);
	public ExpenseCategoryBandEntity  getCompEmployeeBandDetails(String bandid);
	//public ExpenseCategoryBandRequest  getCompEmployeeBandDetails();
	public ExpenseCategoryBandRequest  getCompEmployeeBandDetailsId(Long id,Long employerid);
	public List<ExpenseCategoryBandRequest>  getCompEmployeeBandDetailsList(long employerid);
	public ExpenseCategoryBandRequest  deleteExpenseCategoryBandDetails(ExpenseCategoryBandRequest request);
	public List<ExpenseBandNumberEntity>  getExpenseBandList(Long employerId);
}
