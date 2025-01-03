package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreatedRequest {
	private Long orgId;
	private Long workflowid;
	private String accNumber;
	private String timePeriod;
	private Long id;
}
