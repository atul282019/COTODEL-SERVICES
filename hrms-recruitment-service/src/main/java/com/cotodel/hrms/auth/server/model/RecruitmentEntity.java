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
@Table(name="recruitment")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="recruitment_seq" , sequenceName="recruitment_seq", allocationSize=1)
public class RecruitmentEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="recruitment_seq")
	@Column(name="recruitment_id")
	private Long recruitmentId;
	
	@OneToOne
    @JoinColumn(name = "position_id")
    private PositionEntity position;	

	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;

	@OneToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
	
	@CreationTimestamp
	@Column(name="Recruitment_date")
	private Date RecruitmentDate;
	private boolean status;
	

	@CreationTimestamp
	@Column(name="creation_date")
	private Date createionDate;
	
	@Column(name="recruitment_by")
	private String recruitmentBy;
	
	private String remarks;
	
}
