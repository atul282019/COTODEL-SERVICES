package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorOnboardingResponse {
	 private boolean status;
	 private String message;
	 private DirectorOnboardingRequest data;
	 private String txnId;
	 private String timestamp;
}
