package com.cotodel.hrms.auth.server.dto;


import java.util.List;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExistUserRoleRequest {
	
    private int employerid ;
    private Long orgId;
    List<UserDetailsDto> userDTO;
    private String createdBy;
    private String response;   
    private String consent;
    private String userMobile;
}
