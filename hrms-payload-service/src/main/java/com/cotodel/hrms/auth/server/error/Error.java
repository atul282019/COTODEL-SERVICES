package com.cotodel.hrms.auth.server.error;

/**
 * @author vinay
 */
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class Error {

	@JsonProperty("code")
	private String code;
	
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("trace")
	private String trace;
	
}
