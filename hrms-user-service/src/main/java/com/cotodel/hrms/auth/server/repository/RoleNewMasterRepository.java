package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;

public interface RoleNewMasterRepository extends  JpaRepository<RoleMasterEntity, Long> {
	
	@Query("select s from RoleMasterEntity s where s.roleDesc =?1 ")
	  public RoleMasterEntity getRoleMAster(String roleDesc);
	
	@Query("select s from RoleMasterEntity s where s.roleId >='9' ")
	  public List<RoleMasterEntity> getRoleMasterList();
}
