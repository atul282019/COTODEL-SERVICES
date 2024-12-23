package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.UserBulkUploadDao;
import com.cotodel.hrms.auth.server.entity.UserBulkUploadEntity;
import com.cotodel.hrms.auth.server.repository.UserBulkUploadRepository;

@Repository
public class UserBulkUploadDaoImpl implements UserBulkUploadDao {

		
	@Autowired
	UserBulkUploadRepository userBulkUploadRepository;

	@Override
	public UserBulkUploadEntity saveUserDetails(UserBulkUploadEntity user) {
		// TODO Auto-generated method stub
		return userBulkUploadRepository.saveAndFlush(user);
	}
	
	
}
