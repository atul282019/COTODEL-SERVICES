package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeIncentiveRequest;
import com.cotodel.hrms.auth.server.model.EmployeeIncentiveEntity;

public interface EmployeeIncentiveService {
	
	public EmployeeIncentiveRequest  saveIncentiveDetails(EmployeeIncentiveRequest request);	
	public List<EmployeeIncentiveEntity>  getEmpIncentiveList(Long empid);
	public EmployeeIncentiveEntity  getEmpIncentive(Long employerid);
}
