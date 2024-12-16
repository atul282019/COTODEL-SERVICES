package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleAddRequest {
	private int orgId;
	private String createdBy;
	
	
//     "mobile": "9911851063",
//     "roleId": 2,
//     "orgId": 70,
//     "createdBy": "Fakhruddin"
     
}
