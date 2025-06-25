package com.cotodel.hrms.auth.server.dto.third;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncriptResponse {
	String encriptData;
	String encriptKey;
}
