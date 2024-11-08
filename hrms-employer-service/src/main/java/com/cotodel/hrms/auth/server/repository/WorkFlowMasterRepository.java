package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.WorkFlowMasterEntity;
@Repository
public interface WorkFlowMasterRepository extends JpaRepository<WorkFlowMasterEntity,Long>{
		
	@Query("select c from WorkFlowMasterEntity c where c.workflowId = ?1and c.type=?2 ")
	WorkFlowMasterEntity  findByWorkFlowId(Long workflowId,String type);
	
	
}
