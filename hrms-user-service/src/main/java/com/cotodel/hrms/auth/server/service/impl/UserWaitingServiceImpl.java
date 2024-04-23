package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserWaitingListDao;
import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;
import com.cotodel.hrms.auth.server.service.UserWaitingService;

@Repository
public class UserWaitingServiceImpl implements UserWaitingService {

	private static final Logger logger = LoggerFactory.getLogger(UserWaitingServiceImpl.class);
	@Autowired
	UserWaitingListDao userWaitingListDao;	
	
	
	@Override
	@Transactional
	public UserWaitingListEntity saveUserDetails(UserWaitingListEntity user) {
		
		Date date = new Date();
		LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		user.setCreated_date(localDate);
		UserWaitingListEntity UserEntity1=null;
		try {
			UserEntity1=userWaitingListDao.saveUserDetails(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
		return UserEntity1;
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
