package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ReputeEmployee;
import com.cotodel.hrms.auth.server.dto.ReputeEmployeeRequest;

public interface ReputeService {
	
	public String  getReputeToken(String code,String url);
	public List<ReputeEmployee>  getReputeEmpList(ReputeEmployeeRequest reputeEmployeeRequest);
	
}
