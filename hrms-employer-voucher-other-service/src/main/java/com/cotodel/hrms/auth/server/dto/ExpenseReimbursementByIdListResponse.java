package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.util.List;

import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseReimbursementByIdListResponse implements Serializable{
	
	 private boolean status;
	 private String message;
	 List<ExpenseReimbursementEntity> list;
	 private String txnId;
	 private String timestamp;
}
