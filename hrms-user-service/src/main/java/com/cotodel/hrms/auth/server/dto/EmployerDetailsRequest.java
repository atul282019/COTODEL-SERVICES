package com.cotodel.hrms.auth.server.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDetailsRequest {
	@NotNull(message = "Employer id cannot be null")
    @Min(value = 1, message = "Employer Id must be greater than or equal to 1")
	private Long employerId;	
	private String legalNameOfBusiness;	
	private String tradeName;	
	private String constitutionOfBusiness;	
	private String orgType;	
	private String address1;	
	private String address2;
	private String districtName;
	private String pincode;	
	private String stateName;
	private String consent;
	private String streetName;
	private String buildingNumber;
	private String buildingName;
	private String location;
	private String floorNumber;
	private String otpStatus;
	private String natureOfPrincipalPlaceOfBusiness;
	private String centerJurisdictionCode;
	private String gstIdentificationNumber;
	private String pan;
	private String createdBy;
	private Long id;
	private String response;
	
}
