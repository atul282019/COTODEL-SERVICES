package com.cotodel.hrms.auth.server.dto.voucher;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedempltionDetail {	
	private String transactionDate;
	private String bankrrn;
	private String marchantName;
	private String amount;
}
