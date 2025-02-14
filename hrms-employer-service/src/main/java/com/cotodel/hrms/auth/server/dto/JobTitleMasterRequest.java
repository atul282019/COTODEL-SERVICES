package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobTitleMasterRequest {
	private Long id;
	private String jobDisc;
	private Long managerLblId;
	private String createdBy;	 
	private Long orgId;
	private String updatedBy;
	private String remarks;
	private String response;
	private int status;
	private LocalDateTime creationDate;	
	private LocalDateTime updationDate;
}