package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserManagerDto {
	 private Long id;
	 private String email ;
	 private String  mobile ;
	 private String username;
	
	 
}
