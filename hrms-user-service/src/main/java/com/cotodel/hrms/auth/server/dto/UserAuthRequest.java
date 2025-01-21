package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRequest {
	
	private String userName;
	private String comId;
	private String otp;
	private String txnId;
	private String captchaId;
	private String captchaValue;
	private String password;
	

}
