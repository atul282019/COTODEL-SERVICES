package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.RewardsTierEntity;

public interface RewardsTierServiceDao {

	public List<RewardsTierEntity> getRewardsTierList(Long orgId);
	
}
