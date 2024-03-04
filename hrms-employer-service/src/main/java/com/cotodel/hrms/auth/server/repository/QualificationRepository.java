package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.QualificationEntity;
@Repository
public interface QualificationRepository extends JpaRepository<QualificationEntity,Long>{
	
	@Query("select s  from QualificationEntity s where s.employeeId = ?1")
	public List<QualificationEntity> findByQualificationId(Long emplid);
}
