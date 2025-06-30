package com.cotodel.hrms.auth.server.dto.third;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncriptThirdResponse {
	private String encriptData;
	private String encriptKey;
	private String clientId;
	private String secretKey;
	private String requestId;
}
