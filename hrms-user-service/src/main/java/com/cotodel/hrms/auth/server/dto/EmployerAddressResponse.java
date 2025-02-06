package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerAddressResponse {
	
	  private boolean status;
	  private String message;
	  List<EmployerProfileAddress> data;
	  private String txnId;
	  private String timestamp;
	  private String authToken;
}
