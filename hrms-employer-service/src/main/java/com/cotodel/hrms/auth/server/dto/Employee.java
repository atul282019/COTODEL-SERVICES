package com.cotodel.hrms.auth.server.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	
	
	private Long employeeId;
	
	
    private Long employer_id;
	
	private String firstName;
	
	private String lastName;
	
	
	private Date dateOfBirth;
	
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
	
}
