package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerMasterRequest {
	private Long id;
	private String managerLblDesc;
	private String createdBy;
	private Long orgId;   
	private int status;
	private String remarks;
	private String response;
}

