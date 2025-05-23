package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOtpRequest {
	private String userName;
	private String password1;
	private String password2;
	private String password3;
	private String password4;
	private String password5;
	private String password6;
	private String sresult;
	private String otp;
	private String mobile;
	private String orderId;
	private String password;
	private String countdown;
}
