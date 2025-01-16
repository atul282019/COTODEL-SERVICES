package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseReimbursementDtoByEmpIdListResponse implements Serializable{
	
	 private boolean status;
	 private String message;
	 List<ExpenseReimbursementDto> list;
	 private String txnId;
	 private String timestamp;
}
