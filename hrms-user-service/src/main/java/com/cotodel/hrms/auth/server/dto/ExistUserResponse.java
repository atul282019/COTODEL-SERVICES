package com.cotodel.hrms.auth.server.dto;


import java.time.LocalDate;
import java.util.List;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExistUserResponse {
	
	private Long id ;	
    private String first_name;
    private String last_name;
    private String dateofbirth ;
    private String  gender;
    private String contact_number;
    private String email ;
    private String address ;
    private String org_type;
    private String  org_name;
    private String  mobile ;
    private LocalDate  created_date ;
    private int  email_verify_status;
    private int  mobile_verify_status;
    private LocalDate email_verify_date;
    private LocalDate mobile_verify_date;
    private String username;
    private String pwd ;
    private int status ;
    private int employerid ;
    private int role_id ;
    private String createdBy;
    List<UserRoleMapperDto> userRole;
    private Long orgId;
    private String[] roleDesc;
    private String response;
}
