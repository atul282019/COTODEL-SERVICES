package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.model.AdvanceRequestSettingEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTravelAdvanceListResponse {
	
	 private boolean status;
	 private String message;
	 AdvanceRequestSettingEntity data;
	 private String txnId;
	 private String timestamp;
}
