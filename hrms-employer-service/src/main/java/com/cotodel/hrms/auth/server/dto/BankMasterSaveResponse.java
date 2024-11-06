package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankMasterSaveResponse {
	
	 private boolean status;
	 private String message;
	 private BankMasterRequest data;
	 private String txnId;
	 private String timestamp;
}
