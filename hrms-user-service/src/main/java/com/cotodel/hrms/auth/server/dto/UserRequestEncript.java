package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestEncript {
private String username;	
	private String email;
	private String mobile;	
	private String orgname;	
	private String noofEmp;	
	private String privacyCheck;	
	private String whatsupCheck;	
	private Integer employerId;	
	private boolean erupistatus ;	
	private String captcha;
}
