package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ReputeDao;
import com.cotodel.hrms.auth.server.entity.ReputeTokenEntity;
import com.cotodel.hrms.auth.server.repository.ReputeTokenRepository;

@Repository
public class ReputeDaoImpl implements ReputeDao {

	@Autowired
	ReputeTokenRepository repository;

	@Override
	public ReputeTokenEntity saveUserDetails(ReputeTokenEntity user) {
		// TODO Auto-generated method stub
		return repository.saveAndFlush(user);
	}

	@Override
	public ReputeTokenEntity getReputeDetails(String mobile) {
		// TODO Auto-generated method stub
		List<ReputeTokenEntity> list=repository.getReputeByMobile(mobile);
		return list.size()>0?list.get(0):null;
	}

		
}
