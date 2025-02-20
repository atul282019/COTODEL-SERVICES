package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.entity.ReputeTokenEntity;

public interface ReputeDao {
	
	public ReputeTokenEntity saveUserDetails(ReputeTokenEntity user);
	public ReputeTokenEntity getReputeDetails(String mobile);
}
