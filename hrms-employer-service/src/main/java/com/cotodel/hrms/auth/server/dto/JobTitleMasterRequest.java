package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}




