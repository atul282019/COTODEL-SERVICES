package com.cotodel.hrms.auth.server.repository;

import java.util.List;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.StateMaster;
@Repository
public interface StateMasterRepository extends JpaRepository<StateMaster, Long> {
	@Query("select s  from StateMaster s where s.state_code = ?1 and s.status=1")
	  public StateMaster getByStateCode(String statecode);
	  
	  @Query("select s   from StateMaster s where s.status='1'")
	  public List<StateMaster> getByStateList();
	  
}
