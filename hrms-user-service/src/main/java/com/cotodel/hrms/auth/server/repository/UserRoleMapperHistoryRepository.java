package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.UserRoleMapperHistoryEntity;

@Repository
public interface UserRoleMapperHistoryRepository extends JpaRepository<UserRoleMapperHistoryEntity, Long> {
	
	
	
	
} 
