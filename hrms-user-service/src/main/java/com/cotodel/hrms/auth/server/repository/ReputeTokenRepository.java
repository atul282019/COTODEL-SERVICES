package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.ReputeTokenEntity;

@Repository
public interface ReputeTokenRepository extends JpaRepository<ReputeTokenEntity, Long> {
	@Query("select s from ReputeTokenEntity s where s.mobile =:mobile  ORDER BY s.createdDate DESC ")
	public List<ReputeTokenEntity> getReputeByMobile(@Param("mobile") String mobile);
} 

