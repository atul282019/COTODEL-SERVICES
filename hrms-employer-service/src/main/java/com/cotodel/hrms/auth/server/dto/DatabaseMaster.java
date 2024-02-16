package com.cotodel.hrms.auth.server.dto;

/**
 * @author vinay
 */
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DatabaseMaster {

	
	private Long id ;
	private int employer_id;
	private String username;
	private String password;
	private String currentschema;
	private String driverclassname ;
	private String jdbcurl;
	private int status;
	private String created_date;
	

}
