package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.SecurityClientAndSecretDao;
import com.cotodel.hrms.auth.server.model.SecurityClientAndSecretEntity;
import com.cotodel.hrms.auth.server.repository.SecurityClientAndSecretRepository;
@Repository
public class SecurityClientAndSecretDaoImpl implements SecurityClientAndSecretDao{

	@Autowired
	SecurityClientAndSecretRepository secretRepository;

	@Override
	public SecurityClientAndSecretEntity getSecurityClientAndSecret(String clientId, String secretId,String bankCode) {
		// TODO Auto-generated method stub
		return secretRepository.findSecurityWithStatus(clientId, secretId,bankCode);
	}


	
}
