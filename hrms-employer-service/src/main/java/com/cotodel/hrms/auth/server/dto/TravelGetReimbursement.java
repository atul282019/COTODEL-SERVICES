package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelGetReimbursement {
	private Long id;
    private String mode;
    private String toBeBookedBy;
    private LocalDate date;
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
	private String limitAmount;
	private String title;
	private String arrivalPreference;
    private String travelSubType;
    private String createdDate;
    private String createdTime;
}
