package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerMaster {

	private Long id;
	private String   employer_code;
	private int    state_code ;
	private int     status;
	private String     employer_pan;
	private String     address;
	private String      employer_level;
	private String      created_date;
	private String     updated_date;
	private Long     contact_numbe;
	private String     email ;
	private String      employer_tin;
	private String      created_by;
	private String     employer_type;
	private String     employer_name ;
	private LicenceMaster licenceMaster ;
	private DatabaseMaster databaseMaster ;



}
