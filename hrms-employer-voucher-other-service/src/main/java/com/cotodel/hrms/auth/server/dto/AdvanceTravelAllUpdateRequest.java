package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceTravelAllUpdateRequest {
	private Long employeeId;
    private Long employerId;
    private String response;
	TravelRequestUpdate travelRequestUpdate;	
}
