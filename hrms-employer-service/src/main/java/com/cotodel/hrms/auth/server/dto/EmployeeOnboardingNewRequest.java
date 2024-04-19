package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
