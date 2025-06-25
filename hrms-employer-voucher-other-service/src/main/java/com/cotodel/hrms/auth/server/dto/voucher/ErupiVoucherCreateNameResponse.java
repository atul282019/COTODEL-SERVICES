package com.cotodel.hrms.auth.server.dto.voucher;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateNameResponse {
	
	 private boolean status;
	 private String message;
	 List<ErupiVoucherCreateOldDto> data;
	 private String txnId;
	 private String timestamp;
}
