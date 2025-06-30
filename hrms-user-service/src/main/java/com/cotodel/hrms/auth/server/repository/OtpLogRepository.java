package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.OtpLogEntity;

@Repository
public interface OtpLogRepository extends JpaRepository<OtpLogEntity, Long> {
	
} 

