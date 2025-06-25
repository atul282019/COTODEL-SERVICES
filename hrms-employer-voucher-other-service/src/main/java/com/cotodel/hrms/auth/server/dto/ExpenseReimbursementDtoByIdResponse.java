package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseReimbursementDtoByIdResponse implements Serializable{
	
	 private boolean status;
	 private String message;
	 ExpenseReimbursementDto data;
	 private String txnId;
	 private String timestamp;
}
