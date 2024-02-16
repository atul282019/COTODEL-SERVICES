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
@Table(name="leave")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="leave_seq" , sequenceName="leave_seq", allocationSize=1)
public class LeaveEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="leave_seq")
	@Column(name="leave_id")
	private Long leaveId;
	
	@OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
	
	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;
	
	@Column(name="leave_type")
	private String leaveType;	

	@CreationTimestamp
	@Column(name="start_date")
	private Date startDate;

	@CreationTimestamp
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="leave_status")
	private String leaveStatus;
	

	@CreationTimestamp
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="approoved_by")
	private String approovedBy;
	

	@CreationTimestamp
	@Column(name="approval_date")
	private Date ApprovalDate;
	
	private String remarks;

	
}
