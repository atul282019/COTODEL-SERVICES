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
public class DeleteUserRoleRequest {
	private Long id;
    private int employerId ;
    private Long orgId;
    List<UserRoleDto> roleDesc;
    private String createdby;
    private String response;   
    private String consent;
    private String userMobile;
    private String userName;
    private String email;
    private String mobile;
}
