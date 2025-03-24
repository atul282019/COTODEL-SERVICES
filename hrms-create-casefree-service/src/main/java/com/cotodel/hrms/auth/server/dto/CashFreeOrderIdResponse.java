package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashFreeOrderIdResponse {
	
	 private boolean status;
	 private String message;
	  OrderIdResponse data;
	  private String timestamp;
}
