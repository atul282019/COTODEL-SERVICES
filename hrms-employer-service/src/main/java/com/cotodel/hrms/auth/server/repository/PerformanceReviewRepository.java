package com.cotodel.hrms.auth.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.PerformanceReview;
@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long>{
	@Query("select s  from PerformanceReview s where s.employeeId = ?1")
	public PerformanceReview getByPerformanceReview(Long empid);
}
