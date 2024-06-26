package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.model.ExpanceTravelAdvanceEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTravelAdvanceListResponse {
	
	 private boolean status;
	 private String message;
	 ExpanceTravelAdvanceEntity data;
	 private String txnId;
	 private String timestamp;
}
