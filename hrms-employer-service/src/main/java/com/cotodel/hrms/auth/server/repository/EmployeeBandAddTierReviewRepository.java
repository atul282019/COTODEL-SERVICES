package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierEntity;
import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierReviewEntity;
@Repository
public interface EmployeeBandAddTierReviewRepository extends JpaRepository<EmployeeBandAddTierReviewEntity,Long>{
	
	

}
