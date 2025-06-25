package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceTravelDto implements Serializable{
	
	private Long id;
	private String sequenceId;
	private String expenseCategory;
	private String name;
	private String depratment;
	private float amount;
	private String currency;
	private LocalDateTime  createdDate ;	
	private String modeOfPayment;
	private int status;
	private String statusMessage;
	private String approvedBy;
	private String remarks;
	private int approvedStatus;
	private String statusRemarks;
	private String requestType;
	private String paymentMode;
	private Float approvedAmount;
}

