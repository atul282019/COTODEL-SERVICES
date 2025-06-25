package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreatedDateWiseRequest {
	private LocalDate fromDate;
	private LocalDate toDate;
	private String bankCode;
	private String response;
}
