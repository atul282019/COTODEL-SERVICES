package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
	 private Long id;
	 private String email ;
	 private String  mobile ;
	 private String username;
	 private List<UserRoleDto> userRole;
	 
}
