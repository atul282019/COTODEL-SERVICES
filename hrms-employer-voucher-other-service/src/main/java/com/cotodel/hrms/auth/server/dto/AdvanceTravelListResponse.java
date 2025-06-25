package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceTravelListResponse {
	
	 private boolean status;
	 private String message;
	 List<AdvanceTravelRequestEntity> data;
	 private String txnId;
	 private String timestamp;
}
