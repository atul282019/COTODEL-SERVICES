package com.cotodel.hrms.auth.server.dto.voucher;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreatedRedeemTransactionListResponse {
	
	 private boolean status;
	 private String message;
	 List<ErupiVoucherCreatedRedeemTransactionImgDto> data;
	 private String txnId;
	 private String timestamp;
}
