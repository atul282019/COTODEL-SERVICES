package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseReimbursementDto implements Serializable{
	
	private Long id;
	private String sequenceId;
	private String expenseCategory;
	private String name;
	private String depratment;
	private String amount;
	private LocalDate createationDate;
}
