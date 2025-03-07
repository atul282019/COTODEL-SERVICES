package com.cotodel.hrms.auth.server.dto.indianbank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherCreateIndianBankRequest {
	private String corpid;
	private String corpmobno;
	private String corpupiID;
	private String corpaccNo;
	private String corpaccType;
	private String corpifsc;
	private String corpname;
	private String benemobNo;
	private String benename;
	private String benemailId;
	private String beneIdName;
	private String beneIdno;
	private String purposecode;
	private String revocable;
	private String amount;
	private String validitystartdate;
	private String validityenddate;
	private String initiationMode;
	private String merchantid;
	private String payeeVPA;
	private String payermcc;
	private String payeemcc;
	private String recurrencePattern;
	private String checksum;
}

