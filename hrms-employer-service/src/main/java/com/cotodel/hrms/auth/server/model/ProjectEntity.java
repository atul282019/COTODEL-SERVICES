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
@Table(name="project")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="project_seq" , sequenceName="project_seq", allocationSize=1)
public class ProjectEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="project_seq")	
	@Column(name="id")
	private Long id;
	@Column(name="employee_id")
	private Long employeeId;
	@Column(name="employer_id")
	private Long employerId;
	private String region;
	@Column(name="project_name")
	private String projectName;
	@Column(name="role_in_project")
	private String roleInProject;
	@Column(name="assign_from_date")
	private LocalDate assignFromDate;
	@Column(name="assign_to_date")
	private LocalDate assignToDate;
	@Column(name="sharing_percentage")
	private String sharingPercentage;	
	@Column(name="technical_support")
	private String technicalSupport;
	private String remarks;
}
