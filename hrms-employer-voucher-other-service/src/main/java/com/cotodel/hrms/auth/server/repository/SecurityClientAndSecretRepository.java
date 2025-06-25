package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.SecurityClientAndSecretEntity;
@Repository
public interface SecurityClientAndSecretRepository extends JpaRepository<SecurityClientAndSecretEntity,Long>{
	
	@Query("select s  from SecurityClientAndSecretEntity s where s.clientId =:clientId and s.secretId =:secretId and s.bankCode=:bankCode and s.status='1' ")
	  public SecurityClientAndSecretEntity findSecurityWithStatus(@Param("clientId") String clientId,@Param("secretId") String secretId,@Param("bankCode") String bankCode);
}