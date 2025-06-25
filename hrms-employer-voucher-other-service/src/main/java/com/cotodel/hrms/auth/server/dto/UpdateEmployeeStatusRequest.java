package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeStatusRequest {
	private Long id;
	private Long userDetailsId;
	private Long employerId;
	private String status;
	private String response;
 		
}
