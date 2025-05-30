package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceTravelByIdResponse {
	
	 private boolean status;
	 private String message;
	 AdvanceTravelRequestEntity data;
	 private String txnId;
	 private String timestamp;
}
