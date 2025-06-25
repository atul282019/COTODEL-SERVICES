package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingUserListDto {
	 private Long id;
	 private String email ;
	 private String  mobile ;
	 private String username;
	 private String empCode;
	 private byte[] empPhoto;
}
