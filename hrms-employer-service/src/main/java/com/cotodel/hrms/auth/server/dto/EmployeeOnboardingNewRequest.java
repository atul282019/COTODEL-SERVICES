package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingNewRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
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
	private boolean updateStatus ;
    private boolean emailStatus ;
    private String proofOfIdentity;
    private String pan;
    private String bankAccountNumber;
    private String ifscCode;
    private String beneficiaryName;    
	private String response;
	private Long userDetailsId;
	private Long employeeId;
	private Long managerId;
	private byte[] empPhoto;
	private String clientKey;
	private String hash;
	private String filename;
	private String filetype;
	

	
}
