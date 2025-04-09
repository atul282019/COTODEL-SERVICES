package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.JobTitleMasterDao;
import com.cotodel.hrms.auth.server.dto.JobTitleMasterRequest;
import com.cotodel.hrms.auth.server.model.JobTitleMasterEntity;
import com.cotodel.hrms.auth.server.service.JobTitleMasterService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

@Repository
public class JobTitleMasterServiceImpl implements JobTitleMasterService{
	private static final Logger logger = LoggerFactory.getLogger(JobTitleMasterServiceImpl.class);

	@Autowired
	JobTitleMasterDao jobTitleMasterDao;
		
	
//	@Override
//	public JobTitleMasterRequest saveJobTitleMaster(JobTitleMasterRequest request) {
//		String response = "";
//		JobTitleMasterEntity jobTitleMasterEntity = null;
//		try {
//			response = MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//			jobTitleMasterEntity = new JobTitleMasterEntity();
//
//			CopyUtility.copyProperties(request, jobTitleMasterEntity);
//			jobTitleMasterEntity.setCreationDate(LocalDateTime.now());
//			jobTitleMasterEntity.setStatus(1);
//			jobTitleMasterEntity = jobTitleMasterDao.saveDetails(jobTitleMasterEntity);
//			response = MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//
//		} catch (Exception e) {
//			response = MessageConstant.RESPONSE_FAILED;
//			// e.printStackTrace();
//			request.setResponse(response);
//		}
//		return request;
//	}
//
//
//	@Override
//	public List<JobTitleMasterEntity> getJobTitleMaster(Long orgId) {
//		List<JobTitleMasterEntity> jobTitleMasterEntities=new ArrayList<>();
//		try {
//			jobTitleMasterEntities=jobTitleMasterDao.getJobTitleDetails(orgId);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return jobTitleMasterEntities;
//	}

	
}
