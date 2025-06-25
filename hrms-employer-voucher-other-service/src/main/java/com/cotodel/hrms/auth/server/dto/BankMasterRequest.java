package com.cotodel.hrms.auth.server.dto;

import javax.validation.constraints.NotBlank;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankMasterRequest {
	private Long id;
	@NotBlank(message = "bankCode cannot be blank")
	 private String bankCode;
	@NotBlank(message = "bankName cannot be blank")
	 private String bankName;
	 private int status;
	 private byte[] bankLogo;
	 private String ifsc;
	 private String response;
}
