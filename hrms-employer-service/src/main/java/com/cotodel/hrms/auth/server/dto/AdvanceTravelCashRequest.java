package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceTravelCashRequest {
	
	private Long id;
    private Long employerId;
    private Long employeeId;
	private String requestType;	
	private String expenseCategoryId;
	private String expenseCategory;	
	private String mode;	
	private String toBeBookedBy;	
	private LocalDate travelDate;	
	private String departureLocation;	
	private String arrivalLocation;	
	private String preferredTimeBefore;	
	private String preferredTimeArrival;	
	private String carrierDetails;	
	private String modeOfPayment;	
	private String currency;	
	private String amount;	
	private String approvedAmount;
	private String createdBy;	
	private String remarks;
	private LocalDate cashDate;
	private String response;
	private int status;
}
