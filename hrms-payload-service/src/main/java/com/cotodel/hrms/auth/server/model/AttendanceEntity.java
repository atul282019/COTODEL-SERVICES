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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="attendance")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="attendance_seq" , sequenceName="attendance_seq", allocationSize=1)
public class AttendanceEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="attendance_seq")
	@Column(name="attendance_id")
	private Long attendanceId;	
	
	@OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
	
	@Column(name="attendance_date")
	private Date attendanceDate;
	
	@Column(name="clock_in_time")
	private Date clockInTime;
	
	@Column(name="clock_out_time")
	private Date clockOutTime;
	
	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;
	
	
	
}
