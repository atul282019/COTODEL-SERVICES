package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ProjectEntity;
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity,Long>{
	
	@Query("select s  from ProjectEntity s where s.employeeId = ?1")
	public List<ProjectEntity> findByProjectId(Long emplid);
}
