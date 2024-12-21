package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingRequest implements Serializable {
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
	private Long managerId;
	private String managerLbl;
	private Long jobTitleId;    
	private String response;
	
}
