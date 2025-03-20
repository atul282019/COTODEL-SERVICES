package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
