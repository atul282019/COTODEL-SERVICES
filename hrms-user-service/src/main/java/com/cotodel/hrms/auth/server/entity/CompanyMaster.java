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
@Table(name="company_mst")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="company_mst_seq" , sequenceName="company_mst_seq", allocationSize=1)
public class CompanyMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="company_mst_seq")
	private Long id;
	@Id
	private String code;
	private String industry;
	private String sector;
	private String headquarters;
	private String founded;
	private String notes;
	private String name;

}
