package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDetailsResponse {
	
	  private boolean status;
	  private String message;
	  EmployerDetailsRequest data;
	  private String txnId;
	  private String timestamp;
	  private String authToken;
}
