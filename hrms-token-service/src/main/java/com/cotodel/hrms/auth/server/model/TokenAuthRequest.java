package com.cotodel.hrms.auth.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenAuthRequest {
	
	private String txnId;
	private String companyCode;
	private String authToken;
	private String userName;
	

}
