package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.DirectorOnboardingEntity;
import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorOnboardingGetResponse {
	 private boolean status;
	 private String message;
	 private List<DirectorOnboardingEntity> data;
	 private String txnId;
	 private String timestamp;
}
