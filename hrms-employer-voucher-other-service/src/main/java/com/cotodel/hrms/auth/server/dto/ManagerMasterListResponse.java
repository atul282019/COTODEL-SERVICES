package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ManagerLblMasterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerMasterListResponse {	
	
	 private boolean status;
	 private String message;
	 List<ManagerLblMasterEntity> data;
	 private String txnId;
	 private String timestamp;
}

