package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.WorkFlowMasterEntity;
@Repository
public interface WorkFlowMasterRepository extends JpaRepository<WorkFlowMasterEntity,Long>{
		
	
	@Query("select s  from WorkFlowMasterEntity s where s.workflowId = ?1 and s.type = ?2")
	public WorkFlowMasterEntity findByWorkFlowId(Long id,String  type);
	
	@Query("select s  from WorkFlowMasterEntity s where s.workflowId = ?1")
	public WorkFlowMasterEntity findByWorkFlowId(Long id);
	
}
