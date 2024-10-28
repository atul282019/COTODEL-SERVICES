package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallApiResponse {
	
	 private boolean status;
	 private String message;
	 CallApiVoucherCreateResponse data;
	 private String timestamp;
	
	
}
