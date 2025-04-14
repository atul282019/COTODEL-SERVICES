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
	private Long id;
    private int employerId ;
    private Long orgId;
    List<UserDetailsDto> userDTO;
    private String createdBy;
    private String response;   
    private String consent;
    private String userMobile;
    private String userName;
    private String email;
    private String mobile;
    private String clientKey;
	private String hash;
}
	