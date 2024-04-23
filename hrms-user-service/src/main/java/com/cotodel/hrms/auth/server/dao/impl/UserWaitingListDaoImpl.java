package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.SignUpDao;
import com.cotodel.hrms.auth.server.dao.UserWaitingListDao;
import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.entity.SignUpEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;
import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;
import com.cotodel.hrms.auth.server.repository.SignUpRepository;
import com.cotodel.hrms.auth.server.repository.UserWaitingListRepository;

@Repository
public class UserWaitingListDaoImpl implements UserWaitingListDao {

	@Autowired
	UserWaitingListRepository userWaitingListRepository;

	@Override
	public UserWaitingListEntity saveUserDetails(UserWaitingListEntity userWaitingListEntity) {
		// TODO Auto-generated method stub
		return userWaitingListRepository.saveAndFlush(userWaitingListEntity);
	}

	@Override
	public UserWaitingListEntity getUser(String email) {
		// TODO Auto-generated method stub
		return userWaitingListRepository.getByUser(email);
	}

	@Override
	public List<UserWaitingListEntity> getUserList() {
		// TODO Auto-generated method stub
		return userWaitingListRepository.getByUserList();
	}
	
	
}
