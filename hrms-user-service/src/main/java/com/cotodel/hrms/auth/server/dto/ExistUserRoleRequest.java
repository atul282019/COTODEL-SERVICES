package com.cotodel.hrms.auth.server.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExistUserRoleRequest {
	
    private int employerid ;
    private Long orgId;
    List<UserDetailsDto> userDTO;    
    //private String[] roleDesc;
    private String createdBy;
    private String response;   
    private String consent;
}
