package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.SecurityClientAndSecretEntity;

public interface SecurityClientAndSecretDao {
	
	public SecurityClientAndSecretEntity getSecurityClientAndSecret(String clientId,String secretId,String bankCode);
}
