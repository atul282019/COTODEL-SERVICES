package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalTravelReimbursementById {
	private Long id;
	private Long employeeId;
	private Long employerId;	
    private String requestType;
    private String travelReimbursement;
	private int status;
	private String response;
	private String clientKey;
	private String hash;
}
