package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherBankListDto {
	private String bankAccount;
	private String bankAccountMask;
	private String bankName;      
    private byte[] bankLogo;
}
