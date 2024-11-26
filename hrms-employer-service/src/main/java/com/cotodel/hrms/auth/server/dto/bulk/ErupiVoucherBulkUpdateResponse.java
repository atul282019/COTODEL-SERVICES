package com.cotodel.hrms.auth.server.dto.bulk;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreateSummaryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherBulkUpdateResponse {
	private boolean status;
	 private String message;
	 private int result;
	 private String txnId;
	 private String timestamp;
	
}
