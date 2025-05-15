package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ReputeEmployee;
import com.cotodel.hrms.auth.server.dto.ReputeEmployeeRequest;
import com.cotodel.hrms.auth.server.dto.ReputeEmployeeSingleRequest;
import com.cotodel.hrms.auth.server.dto.ReputeSingleEmployee;

public interface ReputeService {
	
	public String  getReputeToken(String code,String url);
	public List<ReputeEmployee>  getReputeEmpList(ReputeEmployeeRequest reputeEmployeeRequest);
	public ReputeEmployee  getReputeEmpSingle(ReputeEmployeeSingleRequest reputeEmployeeRequest);
	
}
