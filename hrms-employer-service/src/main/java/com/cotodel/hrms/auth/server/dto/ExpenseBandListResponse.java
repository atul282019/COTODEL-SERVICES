package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.model.ExpenseBandNumberEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseBandListResponse {
	
	 private boolean status;
	 private String message;
	 ExpenseBandNumberEntity data;
	 private String txnId;
	 private String timestamp;
}
