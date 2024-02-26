package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.RolePermissionsMaster;

public interface RolePermissionsMasterRepository extends JpaRepository<RolePermissionsMaster, Integer> {
	
	@Query("select s  from RolePermissionsMaster s where s.status=1 and s.employer_id=?1")
	  public List<RolePermissionsMaster> getByRolePermissionsList(int employerId);

}
