package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherTotalDetailDto {
	private String totalIssueCount; 
	private Float totalIssueAmount;
    private String redemVCount;
    private Float redemVAmount;
    private String expRevokeCount;
    private Float expRevokeAmount;
    private String activeCount;
    private Float activeAmount;
}
