package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.CertificateEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateGetResponse {
	
	 private boolean status;
	 private String message;
	 List<CertificateEntity> data;
	 private String txnId;
	 private String timestamp;
}
