package com.cotodel.hrms.auth.server.repository;

import java.util.List;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.EmployerMaster;

@Repository
public interface EmployerMasterRepository extends JpaRepository<EmployerMaster, Long> {
	

  @Query("select s  from EmployerMaster s where s.employer_code = ?1 and s.status=1")
  public EmployerMaster getByEmployerCode(String employerCode);
  
  @Query("select s  from EmployerMaster s where s.status=1")
  public List<EmployerMaster> getByEmployerList();
  
  
}
