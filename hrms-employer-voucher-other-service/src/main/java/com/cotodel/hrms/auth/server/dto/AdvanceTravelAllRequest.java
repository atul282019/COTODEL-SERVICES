package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceTravelAllRequest {
	
    private Long employerId;
	private List<TravelGetReimbursement> travelReimbursement;
	private List<TravelGetReimbursement> mealReimbursement;
	private List<TravelGetReimbursement> miscellaneousReimbursement;
	private List<TravelGetReimbursement> inCityCabReimbursement;
	private List<TravelGetReimbursement> accomodationReimbursement;
	private List<TravelGetReimbursement> cash;
	
}
