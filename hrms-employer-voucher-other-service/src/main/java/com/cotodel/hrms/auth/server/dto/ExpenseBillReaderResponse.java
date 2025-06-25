package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseBillReaderResponse {
	 private boolean status;
	 private String message;
	 ExpenseBillReader data;
	 private String txnId;
	 private String timestamp;

}
