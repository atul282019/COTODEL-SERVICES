package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;

import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseReimbursementByIdResponse implements Serializable{
	
	 private boolean status;
	 private String message;
	 ExpenseReimbursementEntity data;
	 private String txnId;
	 private String timestamp;
}
