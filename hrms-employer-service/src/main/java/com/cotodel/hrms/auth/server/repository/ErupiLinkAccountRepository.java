package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiLinkAccountEntity;
@Repository
public interface ErupiLinkAccountRepository extends JpaRepository<ErupiLinkAccountEntity,Long>{
	
	@Query("select s  from ErupiLinkAccountEntity s where s.orgId = ?1")
	  public ErupiLinkAccountEntity findByOrgId(Long orgId);
	
	@Query("select s  from ErupiLinkAccountEntity s where s.orgId = ?1")
	  public List<ErupiLinkAccountEntity> findErupiListByOrgId(Long orgId);
	
	@Query("select s  from ErupiLinkAccountEntity s where s.acNumber = ?1")
	  public ErupiLinkAccountEntity findErupiAccNumber(String accNumber);
}
