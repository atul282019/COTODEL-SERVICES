package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ReputeDao;
import com.cotodel.hrms.auth.server.dto.ReputeTokenRequest;
import com.cotodel.hrms.auth.server.entity.ReputeTokenEntity;
import com.cotodel.hrms.auth.server.service.ReputeService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@Repository
public class ReputeServiceImpl implements ReputeService {

	private static final Logger logger = LoggerFactory.getLogger(ReputeServiceImpl.class);
	@Autowired
	ReputeDao reputeDao;	
	
	
	@Override
	public ReputeTokenRequest saveReputeDetails(ReputeTokenRequest user) {
		ReputeTokenEntity reputeTokenEntity= new ReputeTokenEntity();
		user.setResponse(MessageConstant.RESPONSE_FAILED);
		try {
			
				reputeTokenEntity= new ReputeTokenEntity();
				CopyUtility.copyProperties(user,reputeTokenEntity);
				reputeTokenEntity.setCreatedDate(LocalDateTime.now());
				reputeTokenEntity=reputeDao.saveUserDetails(reputeTokenEntity);
				user.setResponse(MessageConstant.RESPONSE_SUCCESS);
			
			
		}catch (DataIntegrityViolationException e) {
			user.setResponse(MessageConstant.USER_BULK_EXIST);
		}
		catch (Exception e) {
			e.printStackTrace();
			user.setResponse(MessageConstant.RESPONSE_FAILED);
		}
		
				
		return user;
	}


	@Override
	public ReputeTokenEntity getReputeToken(String mobile) {
		ReputeTokenEntity reputeTokenEntity= new ReputeTokenEntity();
		try {
			reputeTokenEntity=reputeDao.getReputeDetails(mobile);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return reputeTokenEntity;
	}


	
}
