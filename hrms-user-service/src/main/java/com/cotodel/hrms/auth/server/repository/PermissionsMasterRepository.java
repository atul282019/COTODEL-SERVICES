package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cotodel.hrms.auth.server.entity.PermissionsMaster;
import com.cotodel.hrms.auth.server.entity.RoleMaster;

public interface PermissionsMasterRepository extends JpaRepository<RoleMaster, Long> {
	
	@Query("select s  from PermissionsMaster s where s.status=1 and s.employer_id=?1")
	  public List<PermissionsMaster> getByPermissionsList(int employerId);

}
