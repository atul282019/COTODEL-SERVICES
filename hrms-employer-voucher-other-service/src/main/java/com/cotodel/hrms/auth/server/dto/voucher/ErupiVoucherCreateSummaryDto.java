package com.cotodel.hrms.auth.server.dto.voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateSummaryDto {
	private Long count;
    private Long totalAmount;
    private String voucherName;
    private byte[] voucherIcon;
}
