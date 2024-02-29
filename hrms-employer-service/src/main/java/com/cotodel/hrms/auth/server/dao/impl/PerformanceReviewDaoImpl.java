package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.PerformanceReviewDao;
import com.cotodel.hrms.auth.server.model.PerformanceReview;
import com.cotodel.hrms.auth.server.repository.PerformanceReviewRepository;
@Repository
public class PerformanceReviewDaoImpl implements PerformanceReviewDao{

	@Autowired
	PerformanceReviewRepository performanceReviewRepository;

	@Override
	public PerformanceReview saveDetails(PerformanceReview performanceReview) {
		// TODO Auto-generated method stub
		return performanceReviewRepository.saveAndFlush(performanceReview);
	}

	@Override
	public PerformanceReview getPerformanceReview(Long employeeId) {
		// TODO Auto-generated method stub
		return performanceReviewRepository.getByPerformanceReview(employeeId);
	}
	
	
	


}
