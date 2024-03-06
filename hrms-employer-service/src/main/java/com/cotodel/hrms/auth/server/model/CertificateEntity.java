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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_certificate")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_certificate_seq" , sequenceName="employee_certificate_seq", allocationSize=1)
public class CertificateEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_certificate_seq")	
	@Column(name="id")
	private Long id;
	@Column(name="employee_id")
	private Long employeeId;
	@Column(name="employer_id")
	private Long employerId;
	@Column(name="doc_name")
	private String docName;
	private String institutes;
	@Column(name="doc_type")
	private String docType;
	@Column(name="doc_no")
	private String docNo;
	@Column(name="doc_date")
	private LocalDate docDate;	
	private String remarks;
}
