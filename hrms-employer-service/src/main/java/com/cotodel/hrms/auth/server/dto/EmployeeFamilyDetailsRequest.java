package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFamilyDetailsRequest {
	private Long employerId;
	private Long employeeId;	
	private String name;
	private LocalDate dob;	
	private String relation;
	private String nominee;
	private String insuranceNo;
	private String mobile;
	private String email;
	private String remarks;
	private String response;
	}
