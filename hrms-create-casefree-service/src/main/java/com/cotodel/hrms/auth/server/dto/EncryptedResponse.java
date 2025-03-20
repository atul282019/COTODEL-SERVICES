package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncryptedResponse {
	private String requestId;
    private String service;
    private String iv;
    private String encryptedData;
    private String oaepHashingAlgorithm;
    private String clientInfo;
    private String optionalParam;
    private String encryptedKey;
}
