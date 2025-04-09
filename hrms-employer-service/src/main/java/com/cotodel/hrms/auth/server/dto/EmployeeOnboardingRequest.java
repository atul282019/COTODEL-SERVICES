package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Lob;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
   	private Long employerId;
   	private Long employeeId;	
   	private String empOrCont;
   	private String name;
   	private String email;
   	private String mobile;
   	private LocalDate herDate;
   	private String jobTitle;		
   	private String depratment;
   	private Long managerId;
   	private String ctc;
   	private String location;
   	private String residentOfIndia;
   	private boolean updateStatus ;
    private boolean emailStatus ;
    private String proofOfIdentity;
    private String pan;
    private String bankAccountNumber;
    private String ifscCode;
    private String beneficiaryName;    
   	private String response;
   	private byte[] empPhoto;
   	private String managerName;  
	private Long userDetailsId;
	private String clientKey;
	private String hash;

}
