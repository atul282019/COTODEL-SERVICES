package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.RewardsTierEntity;
@Repository
public interface RewardsTierRepository extends JpaRepository<RewardsTierEntity,Long>{
		
	@Query("select s  from RewardsTierEntity s WHERE s.orgId=:orgId order by s.creationDate desc")
	public List<RewardsTierEntity> getRewardsTierList(@Param("orgId") Long orgId);
	
}
