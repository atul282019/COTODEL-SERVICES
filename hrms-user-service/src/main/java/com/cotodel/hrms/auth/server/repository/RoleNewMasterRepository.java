package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;

public interface RoleNewMasterRepository extends  JpaRepository<RoleMasterEntity, Long> {
	

}
