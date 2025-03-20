package com.cotodel.hrms.auth.server.error;

/**
 * @author vinay
 */
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	@NotNull
	@JsonProperty("timestamp")
	private Date timestamp=null;
	
	@JsonProperty("api_call_id")
	private UUID apiCallID;
	
	
	@NotNull
	@JsonProperty("correlation_id")
	private UUID correlationId=null;
	
	@NotNull
	@JsonProperty("error")
	private Error error=null;
	
}
