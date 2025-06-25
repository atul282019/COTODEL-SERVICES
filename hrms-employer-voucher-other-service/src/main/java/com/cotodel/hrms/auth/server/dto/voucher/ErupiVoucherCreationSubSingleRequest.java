package com.cotodel.hrms.auth.server.dto.voucher;

import java.time.LocalDate;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreationSubSingleRequest {

	private Long requestId;
	private Integer voucherId;
	private String name;	
	private String mobile;			
	private String amount;	
    private LocalDate startDate;			
    private LocalDate expDate;	
    private String purposeCode;	
	private String mcc;	
	private String type;	
	private String voucherCode;
	private String voucherType;
	private String voucherDesc;
	private String validity;
	private String bankcode;
	private String mccDescription;
	private String purposeDescription;
	private String redemptionType;
	private String response;
}
