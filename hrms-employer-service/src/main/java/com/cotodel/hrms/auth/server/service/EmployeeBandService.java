package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeBandRequest;
import com.cotodel.hrms.auth.server.model.EmployeeBandEntity;

public interface EmployeeBandService {
	
	public EmployeeBandRequest  saveCompEmployeeBandDetails(EmployeeBandRequest request);
	public EmployeeBandEntity  getCompEmployeeBandDetails(String bandid);
	public List<EmployeeBandEntity> getEmployeeBandList();
}
