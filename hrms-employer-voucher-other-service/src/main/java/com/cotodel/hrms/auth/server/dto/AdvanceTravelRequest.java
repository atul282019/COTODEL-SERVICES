package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceTravelRequest {
	
	private Long id;
    private Long employerId;
    private Long employeeId;
	private String requestType;
	private int status;
	private String response;
	private List<TravelReimbursement> travelReimbursement;
	private  String clientKey;
	private  String hash;
}
