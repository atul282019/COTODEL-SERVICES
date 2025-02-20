package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserWaitingListDao;
import com.cotodel.hrms.auth.server.dto.UserWaitingListRequest;
import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;
import com.cotodel.hrms.auth.server.service.UserWaitingService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@Repository
public class UserWaitingServiceImpl implements UserWaitingService {

	private static final Logger logger = LoggerFactory.getLogger(UserWaitingServiceImpl.class);
	@Autowired
	UserWaitingListDao userWaitingListDao;	
	
	
	@Override
	public UserWaitingListRequest saveUserDetails(UserWaitingListRequest user) {
		UserWaitingListEntity userWaitingListEntity= new UserWaitingListEntity();
		user.setResponse(MessageConstant.RESPONSE_FAILED);
		try {
			CopyUtility.copyProperties(user,userWaitingListEntity);
			userWaitingListEntity.setStatus(0);
			userWaitingListEntity.setCreatedDate(LocalDateTime.now());
			userWaitingListEntity=userWaitingListDao.saveUserDetails(userWaitingListEntity);
			user.setResponse(MessageConstant.RESPONSE_SUCCESS);
		}catch (DataIntegrityViolationException e) {
			user.setResponse(MessageConstant.USER_EMAIL);
		}
		catch (Exception e) {
			e.printStackTrace();
			user.setResponse(MessageConstant.RESPONSE_FAILED);
		}
		
				
		return user;
	}


	@Override
	public UserWaitingListEntity checkUserEmail(String userEmail) {
		// TODO Auto-generated method stub
		return userWaitingListDao.getUser(userEmail);
	}


	@Override
	public List<UserWaitingListEntity> checkUserList() {
		// TODO Auto-generated method stub
		return userWaitingListDao.getUserList();
	}

	
}
