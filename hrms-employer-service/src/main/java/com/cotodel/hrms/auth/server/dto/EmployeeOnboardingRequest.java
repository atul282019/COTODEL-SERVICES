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
public class EmployeeOnboardingRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long employerId;
	private Long orgId;
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
	private String username;
	private byte[] empPhoto;
	
}
