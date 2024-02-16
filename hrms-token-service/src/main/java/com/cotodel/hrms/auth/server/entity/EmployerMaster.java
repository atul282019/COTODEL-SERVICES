package com.cotodel.hrms.auth.server.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employer_mst")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employer_mst_seq" , sequenceName="employer_mst_seq", allocationSize=1)
public class EmployerMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employer_mst_seq")
	private Long id;
	 private String   employer_code;
	 private int    state_code ;
	 private int     status;
	 private String     employer_pan;
	 private String     address;
	 private String      employer_level;
	 private LocalDate      created_date;
	 private LocalDate     updated_date;
	 private Long     contact_numbe;
	 private String     email ;
	 private String      employer_tin;
	 private String      created_by;
	 private String     employer_type;
	 private String     employer_name ;
	 
	 @OneToOne
	 @JoinTable(name = "licence_mst", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "employer_id"))
	  private LicenceMaster licenceMaster ;
	 
	 
	 @OneToOne
	 @JoinTable(name = "db_mst", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "employer_id"))
	  private DatabaseMaster databaseMaster ;
	 
	 
	 
}
