package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
	private Long employerId;
	private Long employeeId;
	private String region;
	private String projectName;
	private String roleInProject;
	private LocalDate assignFromDate;
	private LocalDate assignToDate;
	private String sharingPercentage;	
	private String technicalSupport;	
	private String response;
	
	}
