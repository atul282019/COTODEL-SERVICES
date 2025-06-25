package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.reward.RewardsTierRequest;
import com.cotodel.hrms.auth.server.model.RewardsTierEntity;

public interface RewardsTierService {	
	
	public List<RewardsTierEntity>  getRewardsTierList(RewardsTierRequest request);
	
}
