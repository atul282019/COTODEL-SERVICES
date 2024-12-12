package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.SignUpDao;
import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.entity.SignUpEntity;
import com.cotodel.hrms.auth.server.repository.SignUpRepository;

@Repository
public class SignUpDaoImpl implements SignUpDao {

	@Autowired
	SignUpRepository signUpRepository;
	
	
		
	
	@Override
	public SignUpEntity saveUserDetails(SignUpEntity user) {
		// TODO Auto-generated method stub
		return signUpRepository.saveAndFlush(user);
	}




	@Override
	public List<UserDto> getUser(int employerID) {
		// TODO Auto-generated method stub
		return signUpRepository.findByEmployerId(employerID);
	}



}
