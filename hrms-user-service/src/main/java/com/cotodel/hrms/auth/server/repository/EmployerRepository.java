package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.EmployerEntity;
import com.cotodel.hrms.auth.server.entity.SignUpEntity;

@Repository
public interface EmployerRepository extends JpaRepository<EmployerEntity, Long> {
	
  
} 

