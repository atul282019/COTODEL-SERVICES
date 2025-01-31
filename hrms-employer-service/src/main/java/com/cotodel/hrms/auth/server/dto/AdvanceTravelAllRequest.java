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
	private List<TravelReimbursement> travelReimbursement;
	private List<TravelReimbursement> mealReimbursement;
	private List<TravelReimbursement> miscellaneousReimbursement;
	private List<TravelReimbursement> inCityCabReimbursement;
	private List<TravelReimbursement> accomodationReimbursement;
	
}
