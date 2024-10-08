package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ExperienceEntity;
@Repository
public interface ExperienceRepository extends JpaRepository<ExperienceEntity,Long>{
	
	@Query("select s  from ExperienceEntity s where s.employeeId = ?1")
	public List<ExperienceEntity> findByExperienceId(Long emplid);
}
