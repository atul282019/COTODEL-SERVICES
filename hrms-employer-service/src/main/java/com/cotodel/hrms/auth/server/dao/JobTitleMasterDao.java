package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.JobTitleMasterEntity;

public interface JobTitleMasterDao {
	public JobTitleMasterEntity saveDetails(JobTitleMasterEntity jobTitleMasterEntity);
	public List<JobTitleMasterEntity> getJobTitleDetails(Long orgId);
}
