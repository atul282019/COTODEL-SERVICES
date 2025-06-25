package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelRequestUpdate {
	private List<TravelReimbursement> travelReimbursement;
	private List<TravelReimbursement> mealReimbursement;
	private List<TravelReimbursement> miscellaneousReimbursement;
	private List<TravelReimbursement> inCityCabsReimbursement;
	private List<TravelReimbursement> accommodationReimbursement;
}

