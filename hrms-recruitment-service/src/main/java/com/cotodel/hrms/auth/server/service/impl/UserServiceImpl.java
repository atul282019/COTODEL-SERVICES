package com.cotodel.hrms.auth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.model.UserEntity;
import com.cotodel.hrms.auth.server.repository.UserRepository;
import com.cotodel.hrms.auth.server.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	UserRepository   userRepository;
	
	@Override
	public UserEntity getByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.getByUser(userName);
	}

}
