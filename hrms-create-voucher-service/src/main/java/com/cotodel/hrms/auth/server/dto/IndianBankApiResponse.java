package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndianBankApiResponse {
	
	 private boolean status;
	 private String message;
	 IndianBankVoucherCreateResponse data;
	 private String timestamp;
	
	
}
