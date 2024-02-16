package com.cotodel.hrms.auth.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerifyResponse {
	
	  private boolean status;
	  private String txnId;
	  private String timestamp;
}