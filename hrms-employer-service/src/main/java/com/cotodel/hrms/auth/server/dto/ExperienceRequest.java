package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceRequest {
	private Long employerId;
	private Long employeeId;
	private String designation;
	private String company;	
	private LocalDate fromDate;	
	private LocalDate toDate;
	private String noOfYear;
	private String country;
	private String referenceEmail;
	private String referenceMobile;	
	private  String docfile;
	private  String docFileName;
	private String remarks;
	private String response;
	
	}
