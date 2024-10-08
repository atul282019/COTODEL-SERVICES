package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualificationRequest {
	private Long employerId;
	private Long employeeId;
	private LocalDate fromDate;	
	private LocalDate toDate;
	private String education;
	private String institutes;
	private String referenceType;
	private String remarks;
	private String response;
	private  String docfile;
	private  String docFileName;
	}
