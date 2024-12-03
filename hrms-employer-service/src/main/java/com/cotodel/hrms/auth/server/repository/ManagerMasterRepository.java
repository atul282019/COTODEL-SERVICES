package com.cotodel.hrms.auth.server.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ManagerLblMasterEntity;
@Repository
public interface ManagerMasterRepository extends JpaRepository<ManagerLblMasterEntity,Long>{
	
	@Query("select s  from ManagerLblMasterEntity s where s.orgId = ?1")
	  public List<ManagerLblMasterEntity> findByOrgId(Long orgId);
}
