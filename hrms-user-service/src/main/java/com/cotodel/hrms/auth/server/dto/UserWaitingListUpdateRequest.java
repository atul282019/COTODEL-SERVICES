package com.cotodel.hrms.auth.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWaitingListUpdateRequest{

	private Long id ;	
	private String companyName;	
	private String companySize;	
	private String industry;	
	private String contactPersonName;	
	private String contactNumber;  
	private String email;
	private String response;
	private String status;
	private boolean erupistatus ;
}
