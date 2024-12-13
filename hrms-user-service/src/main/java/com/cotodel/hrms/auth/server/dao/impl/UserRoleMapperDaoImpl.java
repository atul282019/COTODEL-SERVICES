package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserRoleMapperDao;
import com.cotodel.hrms.auth.server.dto.UserRoleMapperDto;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperEntity;
import com.cotodel.hrms.auth.server.repository.UserRoleMapperRepository;

@Repository
public class UserRoleMapperDaoImpl implements UserRoleMapperDao {

	@Autowired
	UserRoleMapperRepository userRoleMapperRepository;

	@Override
	public UserRoleMapperEntity saveUserRoleDetails(UserRoleMapperEntity userRoleMapperEntity) {
		// TODO Auto-generated method stub
		return userRoleMapperRepository.saveAndFlush(userRoleMapperEntity);
	}

	@Override
	public List<UserRoleMapperDto> getUserRoleList(String  mobile) {
		// TODO Auto-generated method stub
		return userRoleMapperRepository.getByMobile(mobile);
	}
	

	
}
