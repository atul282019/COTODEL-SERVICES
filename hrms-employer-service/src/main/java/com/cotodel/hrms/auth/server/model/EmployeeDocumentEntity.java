package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_document")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_document_seq" , sequenceName="employee_document_seq", allocationSize=1)
public class EmployeeDocumentEntity  implements Serializable{
	

	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_document_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="doc_id")
	private Long docId;//FK
	
	
	@OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
	

	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;
	
	@Column(name="document_type")
	private String documentType;
	
	@Column(name="doc_number")
	private String docNumber;
	
	private String url;
	
	@CreationTimestamp
	@Column(name="creation_date")
	private Date creationDate;
	
	@CreationTimestamp
	@Column(name="validation_date")
	private Date validationDate;
	
	@Column(name="validated_by")
	private String validatedBy;
	
	@Column(name="validation_report")
	private String validationReport;
	
	private String remarks;
	

}
