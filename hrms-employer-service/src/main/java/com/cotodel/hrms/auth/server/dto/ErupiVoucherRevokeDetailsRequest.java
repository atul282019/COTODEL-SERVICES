package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherRevokeDetailsRequest {
	

    private String otpValidationStatus;
    private String response;	
	private List<ErupiVoucherRevokeRequest> list;
	
}
