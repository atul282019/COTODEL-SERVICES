package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashFreeOrderUpdateResponse {
	
	 private boolean status;
	 private String message;
	 private OrderUserUpdateRequest data;
	 private String timestamp;
}
