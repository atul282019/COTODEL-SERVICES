package com.cotodel.hrms.auth.server.dto;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequestNew {
	private int orgId;
	private Long id;
	private Long employerId;
	private String userName;
	private String userMobile;
	private String consent;
	private String email;
	private String mobile;
	private String createdby;
	private String roleDesc;
}
