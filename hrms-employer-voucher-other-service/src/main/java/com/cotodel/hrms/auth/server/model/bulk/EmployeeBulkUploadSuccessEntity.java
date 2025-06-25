package com.cotodel.hrms.auth.server.model.bulk;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name="employee_bulk_upload_success")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_bulk_upload_success_seq" , sequenceName="employee_bulk_upload_success_seq", allocationSize=1)
public class EmployeeBulkUploadSuccessEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_bulk_upload_success_seq")
	private Long id;
	
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="user_type")
	private String userType;
	
	@Column(name="beneficiary_name")
	private String beneficiaryName;
	
	@Column(name="mobile")
	private String mobile;	

	@Column(name = "create_flag")
    private String createFlag="N";
	
	@Column(name = "org_id")
    private Long orgId;
	
	@Column(name = "creation_date")
    private LocalDateTime creationDate;
	
	@Column(name="createdby", length=49)
	private String createdby;
	
	@Column(name="bulktbl_id")
	private Long bulktblId;

	@Column(name="status")
	private Long status;
	
}
