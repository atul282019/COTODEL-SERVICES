package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserRoleMapperHistoryDao;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperHistoryEntity;
import com.cotodel.hrms.auth.server.repository.UserRoleMapperHistoryRepository;

@Repository
public class UserRoleMapperHistoryDaoImpl implements UserRoleMapperHistoryDao {

	@Autowired
	UserRoleMapperHistoryRepository userRoleMapperHistoryRepository;

	@Override
	public UserRoleMapperHistoryEntity saveUserRoleDetails(UserRoleMapperHistoryEntity userRoleMapperHistoryEntity) {
		// TODO Auto-generated method stub
		return userRoleMapperHistoryRepository.saveAndFlush(userRoleMapperHistoryEntity);
	}

	

	
}
