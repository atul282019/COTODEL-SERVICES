package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.JobTitleMasterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobTitleMasterListResponse {	
	
	 private boolean status;
	 private String message;
	 List<JobTitleMasterEntity> data;
	 private String txnId;
	 private String timestamp;
}

