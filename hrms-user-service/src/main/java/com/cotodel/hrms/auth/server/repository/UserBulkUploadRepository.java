package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.UserBulkUploadEntity;

@Repository
public interface UserBulkUploadRepository extends JpaRepository<UserBulkUploadEntity, Long> {
	

} 

