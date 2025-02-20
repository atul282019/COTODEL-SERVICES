package com.cotodel.hrms.auth.server.service;

import com.cotodel.hrms.auth.server.dto.ReputeTokenRequest;
import com.cotodel.hrms.auth.server.entity.ReputeTokenEntity;

public interface ReputeService {	
	public ReputeTokenRequest saveReputeDetails(ReputeTokenRequest user);
	public ReputeTokenEntity getReputeToken(String mobile);
}
