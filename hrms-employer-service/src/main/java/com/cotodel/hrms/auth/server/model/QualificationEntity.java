package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_qualification")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_qualification_seq" , sequenceName="employee_qualification_seq", allocationSize=1)
public class QualificationEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_qualification_seq")	
	@Column(name="id")
	private Long id;
	@Column(name="employee_id")
	private Long employeeId;
	@Column(name="employer_id")
	private Long employerId;
	@Column(name="from_date")
	private LocalDate fromDate;	
	@Column(name="to_date")
	private LocalDate toDate;
	private String education;
	private String institutes;
	private String referenceType;
	private String remarks;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name="doc_file")
	private  byte[] docfile;	
	
	@Column(name="doc_file_name")
	private  String docFileName;
}
