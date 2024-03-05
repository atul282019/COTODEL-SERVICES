package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ProjectEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGetResponse {
	
	 private boolean status;
	 private String message;
	 List<ProjectEntity> data;
	 private String txnId;
	 private String timestamp;
}
