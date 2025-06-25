package com.cotodel.hrms.auth.server.dto.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherStatusResponse {
	
	 private boolean status;
	 private String message;
	 ErupiVoucherStatusRequest data;
	 private String txnId;
	 private String timestamp;
}
