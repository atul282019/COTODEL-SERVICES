package com.cotodel.hrms.auth.server.dto.voucher;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreationDetailsResponse {
	
	 private boolean status;
	 private String message;
	 private List<ErupiVoucherCreateDetailsRequest> data;
	 private String txnId;
	 private String timestamp;
}
