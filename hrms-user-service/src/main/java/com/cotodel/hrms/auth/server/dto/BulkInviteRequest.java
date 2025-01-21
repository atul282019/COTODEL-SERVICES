package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkInviteRequest  implements Serializable{
	
	private static final long serialVersionUID = -5145118965670277166L;
	
	private Long employerId;
	private String inviteEmployee;
	private String inviteContractor;		
	private String response;
}
