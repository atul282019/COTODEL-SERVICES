package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

