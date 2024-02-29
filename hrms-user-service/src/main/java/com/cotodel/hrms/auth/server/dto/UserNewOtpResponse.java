package com.cotodel.hrms.auth.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNewOtpResponse {
	
	  private boolean status;
	  private String message;
	  private String orderId;
	  private String txnId;
	  private String timestamp;
}
