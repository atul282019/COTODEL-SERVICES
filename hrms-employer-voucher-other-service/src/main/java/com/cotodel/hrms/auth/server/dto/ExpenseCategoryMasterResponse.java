package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ExpenseCategoryMasterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategoryMasterResponse {
	
	 private boolean status;
	 private String message;
	 private List<ExpenseCategoryMasterEntity> data;
	 private String txnId;
	 private String timestamp;
}
