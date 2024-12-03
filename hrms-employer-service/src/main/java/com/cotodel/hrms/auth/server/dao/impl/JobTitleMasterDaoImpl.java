package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.JobTitleMasterDao;
import com.cotodel.hrms.auth.server.model.JobTitleMasterEntity;
import com.cotodel.hrms.auth.server.repository.JobTitleMasterRepository;
@Repository
public class JobTitleMasterDaoImpl implements JobTitleMasterDao{

	@Autowired
	JobTitleMasterRepository jobTitleMasterRepository;

	@Override
	public JobTitleMasterEntity saveDetails(JobTitleMasterEntity jobTitleMasterEntity) {
		// TODO Auto-generated method stub
		return jobTitleMasterRepository.saveAndFlush(jobTitleMasterEntity);
	}

	@Override
	public List<JobTitleMasterEntity> getJobTitleDetails(Long orgId) {
		// TODO Auto-generated method stub
		return jobTitleMasterRepository.findByOrgId(orgId);
	}

	
	
	

}
