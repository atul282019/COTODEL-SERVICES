package com.cotodel.hrms.auth.server.dto.indianbank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputParamRRequest {	
	private String txnrefID;
	private String umn;
	private String orgTxnId;
	private String orgTxnDate;
	private String orgTxnAmount;
	private String txndatetime;
	private String checkSum;
}