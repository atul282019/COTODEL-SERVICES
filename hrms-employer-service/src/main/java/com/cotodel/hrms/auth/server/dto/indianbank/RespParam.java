package com.cotodel.hrms.auth.server.dto.indianbank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespParam {
	private String txnRefId;
	private String txnDateTime;
	private String umn;
	private String redeemedDate;
}
