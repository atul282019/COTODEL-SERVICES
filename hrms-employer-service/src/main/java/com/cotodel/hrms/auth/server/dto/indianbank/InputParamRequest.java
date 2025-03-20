package com.cotodel.hrms.auth.server.dto.indianbank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputParamRequest {	
	private String txnrefID;
	private String umn;
	private String txndatetime;
	private String orgTxnAmount;
	private String checkSum;
}
