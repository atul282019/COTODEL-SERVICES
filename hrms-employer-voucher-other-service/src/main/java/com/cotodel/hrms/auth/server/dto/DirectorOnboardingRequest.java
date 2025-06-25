package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorOnboardingRequest {
    private Long id;	
	private Long orgId;	
	private String name;	
	private String email;	
	private String mobile;	
	private String din;	
	private String designation;		
	private String address;
	private String createdby;
	private String response;
	private String clientKey;
	private String hash;
	
}
