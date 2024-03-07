package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateRequest {
	private Long employerId;
	private Long employeeId;	
	private String docName;
	private String institutes;
	private String docType;
	private LocalDate docDate;
	private String docNo;
	private String remarks;		
	private String response;
	private  MultipartFile docfile;	
	}
