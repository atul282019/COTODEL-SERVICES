package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelReimbursement {
	private Long id;
	 private Long employeeId;
    private String mode;
    private String toBeBookedBy;
    private String date;
    private String departure;
    private String arrival;
    private String preferredTime;
    private String timePreference;
    private String carrierClass;
    private String carrierDetails;
    private String paymentMode;
    private String remarks;    
    private String hotelDetails;
    private String location;
    private String sequenceId;
    private String  createdDate ;
    private String  createdTime ;
    private LocalDate  checkoutDate;
    private LocalDate checkinDate;
    private String type;
    private String currency;	
	private Float amount;
    private String fromLocation;
    private String toLocation;    
    private String typeOfMeal;
    private LocalDate startDate;
    private String numberOfDays;
    private String requestType;
    private String statusRemarks;
}
