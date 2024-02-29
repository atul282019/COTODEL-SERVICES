package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.PerformanceReview;

public interface PerformanceReviewDao {
	public PerformanceReview saveDetails(PerformanceReview performanceReview);
	public PerformanceReview getPerformanceReview(Long employeeId);
}
