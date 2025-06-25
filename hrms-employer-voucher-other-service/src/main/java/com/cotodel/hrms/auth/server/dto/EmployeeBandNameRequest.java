package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeBandNameRequest {	

	private long employerId;
	private String BandNameOne;
	private String BandNameTwo;
	private String BandNameThree;
	private String BandNameFour;
	private String BandNameFive;
	private String BandNameSix;
	private String response;
}
