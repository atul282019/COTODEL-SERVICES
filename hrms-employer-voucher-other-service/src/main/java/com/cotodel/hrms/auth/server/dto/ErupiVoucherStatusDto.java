package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherStatusDto {
	private Long count;
	private Long totalAmount;
	private String type;    
    private String voucherName;
    private String status;
}
