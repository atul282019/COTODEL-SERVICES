package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
	
	private String code;
	private String industry;
	private String sector;
	private String headquarters;
	private String founded;
	private String notes;
	private String name;
}
