package com.cotodel.hrms.auth.server.dto.voucher;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherStatusRedeemResponse {
	
	private String voucherCode;
	private String voucherDesc;
	private byte[] voucherLogo;
	private String voucherAmount;
	private String activeAmount;
	private String issueDate;
	private String merchantTranId;
	private String name;
	private String mobile;
	private String expDate;
	private String accountNumber;
	private byte[] bankLogo;
	private String amountSpent;
	private String voucherStatus;
	private String response;
	private List<RedempltionDetail> redeemData;
}
