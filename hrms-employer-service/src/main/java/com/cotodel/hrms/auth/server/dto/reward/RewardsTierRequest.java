package com.cotodel.hrms.auth.server.dto.reward;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardsTierRequest {
	private Long orgId;	
	private String response;
}
