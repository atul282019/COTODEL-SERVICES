package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.model.EmployerEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
	
	private EmployerEntity employer;	
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String gender;
	private String Mobile;
	private String picUrl;
	private String email;
	private String address;
	private Long roleId;
	private String username;
	private String pwd;
	private String bankAccount;
	private String ifsc;
	private String urn;
	private String pan;
	private String aadhaar;
	private String extra1;
	private String extra2;
	private boolean status;
	private String intextra1;
	private String response;
}
