package com.cotodel.hrms.auth.server.dto.voucher;
import java.util.List;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateListResponse {
	
	 private boolean status;
	 private String message;
	 List<ErupiVoucherCreateDetailsRequest> data;
	 private String txnId;
	 private String timestamp;	
}
