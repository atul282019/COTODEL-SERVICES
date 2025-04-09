package com.cotodel.hrms.auth.server.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.JobTitleMasterEntity;
@Repository
public interface JobTitleMasterRepository extends JpaRepository<JobTitleMasterEntity,Long>{
	
	@Query("select s  from JobTitleMasterEntity s ")
	  public List<JobTitleMasterEntity> findByOrgId(Long orgId);
}
