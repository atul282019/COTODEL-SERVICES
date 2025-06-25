package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeIncentiveRequest {
	

    private Long employerId;
    private Long employeeId;	
    private String providerFirstName;	
    private String providerLastName;
    private String providerCpaNumber;	
    private String providerPhoneNumber;	
    private String provideEmail;	
    private String nationalInsuranceNumber;	
    private String declaration;	
    private String employeeDetail;	
    private String employeeFirstName;	
    private String employeeLastName;	
    private String companyAddress;	
    private String companyContactPersonName;	
    private String phoneNumber;	
    private String bankName;	
    private String bankAccountName;	
    private String bankAccountNumber;	
    private String jobDetails;	
    private String jobTitle;
    private LocalDate jobStartDate;	
    private String payrollNumber;	
    private String jobType;	
    private String employerClaimDetail;	
    private LocalDate date;	
    private String employerName;	
    private String employerSignatureFile;	
    private String employerSignatureName;
    private String response;
    
}
