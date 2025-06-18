package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.RewardsTierServiceDao;
import com.cotodel.hrms.auth.server.dto.reward.RewardsTierRequest;
import com.cotodel.hrms.auth.server.model.RewardsTierEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.RewardsTierService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class RewardsTierServiceImpl implements RewardsTierService{

	private static final Logger logger = LoggerFactory.getLogger(RewardsTierServiceImpl.class);

	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	RewardsTierServiceDao rewardsTierServiceDao;

	@Override
	public List<RewardsTierEntity> getRewardsTierList(RewardsTierRequest request) {
		return rewardsTierServiceDao.getRewardsTierList(request.getOrgId());
	}
		   

}
