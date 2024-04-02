package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingRequest {
	
	private Long employerId;
	private String empOrCont;
	private String name;
	private String email;
	private String mobile;
	private LocalDate herDate;
	private String jobTitle;
	private String depratment;
	private String managerName;
	private String ctc;
	private String location;
	private String residentOfIndia;	
	private String response;
}
