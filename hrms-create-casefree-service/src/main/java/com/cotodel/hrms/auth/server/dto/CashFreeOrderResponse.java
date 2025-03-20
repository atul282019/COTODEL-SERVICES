package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashFreeOrderResponse {
	
	 private boolean status;
	 private String message;
	  OrderUserRequest data;
	  private String timestamp;
}
