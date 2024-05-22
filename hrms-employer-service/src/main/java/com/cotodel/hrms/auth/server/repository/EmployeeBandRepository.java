package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.EmployeeBandEntity;
@Repository
public interface EmployeeBandRepository extends JpaRepository<EmployeeBandEntity,Long>{
	
//	@Query("select s  from EmployeeBandEntity s where s.employerId = ?1 and s.status='1'")
//	public List<EmployeeBandEntity> findByEmployeeBand(Long employerid);
	
//	@Query("select s  from EmployeeBandEntity s where s.bandId = ?1 ")
//	public EmployeeBandEntity findByEmployeeBandId(String bandid);
	
	@Query("select s  from EmployeeBandEntity s where s.status ='1' ")
	public List<EmployeeBandEntity> findByEmployeeBandList();
	
}
