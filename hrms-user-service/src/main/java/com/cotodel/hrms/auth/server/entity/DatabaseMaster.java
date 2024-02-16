package com.cotodel.hrms.auth.server.entity;

/**
 * @author vinay
 */
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="db_mst")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="db_mst_seq" , sequenceName="db_mst_seq", allocationSize=1)
public class DatabaseMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="db_mst_seq")
	private Long id ;
	private int employer_id;
	private String username;
	private String password;
	private String currentschema;
	private String driverclassname ;
	private String jdbcurl;
	private int status;
	private LocalDate created_date;
	

}
