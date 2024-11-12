package com.cotodel.hrms.auth.server.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankMasterRequest {
	@NotBlank(message = "bankCode cannot be blank")
	 private String bankCode;
	@NotBlank(message = "bankName cannot be blank")
	 private String bankName;
	 private int status;
	 private byte[] bankLogo;
	 private String ifsc;
	 private String response;
}
