package com.cotodel.hrms.auth.server.dto.indianbank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndianBankVoucherCreateResponse {	
	private String txnStatus;
	private String txnMsg;
	private RespParam respParam;			
}
