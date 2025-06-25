package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.RewardsTierServiceDao;
import com.cotodel.hrms.auth.server.model.RewardsTierEntity;
import com.cotodel.hrms.auth.server.repository.RewardsTierRepository;
@Repository
public class RewardsTierServiceDaoImpl implements RewardsTierServiceDao{

	@Autowired
	RewardsTierRepository rewardsTierRepository;

	@Override
	public List<RewardsTierEntity> getRewardsTierList(Long orgId) {
		// TODO Auto-generated method stub
		return rewardsTierRepository.getRewardsTierList(orgId);
	}
	
	

}
