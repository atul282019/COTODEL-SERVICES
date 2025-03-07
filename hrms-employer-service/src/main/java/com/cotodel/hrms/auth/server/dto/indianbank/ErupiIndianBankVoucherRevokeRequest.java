package com.cotodel.hrms.auth.server.dto.indianbank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiIndianBankVoucherRevokeRequest {	
	
	private String action;
	private String subaction;
	private String entityId;
	private InputParamRRequest inputparam;
	
	}
