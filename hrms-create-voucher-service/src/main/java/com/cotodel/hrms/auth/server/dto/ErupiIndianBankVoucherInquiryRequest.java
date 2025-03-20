package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiIndianBankVoucherInquiryRequest {	
	
	private String action;
	private String subaction;
	private String entityID;
	private InputParamRequest inputparam;	
	}
