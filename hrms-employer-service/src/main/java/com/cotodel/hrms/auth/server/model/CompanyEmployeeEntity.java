package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name="company_employee")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="company_employee_seq" , sequenceName="company_employee_seq", allocationSize=1)
public class CompanyEmployeeEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="company_employee_seq")	
	@Column(name="id")
	private Long id;
	
	@Column(name="employer_id")
	private String employerId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="date_Of_birth")
	private LocalDate dateOfBirth;
	
	private String gender;	
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_details_id",referencedColumnName ="id")
	private ContactDetails contactDetails;
   
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employment_details_id",referencedColumnName ="id")
	private EmploymentDetails employmentDetails;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supervisor_id",referencedColumnName ="id")
	private Supervisor supervisor;
	
//	@OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "skills_id",referencedColumnName ="id")
	private String[] skills;
	
//	@OneToMany(cascade = CascadeType.ALL)
//  @JoinColumn(name = "certifications_id",referencedColumnName ="id")
	private String[] certifications;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "emergency_contacts_id",referencedColumnName ="id")
	List<EmergencyContacts> emergencyContacts;
	
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "leave_request_id",referencedColumnName ="id")
//	LeaveRequest leaveRequest;
//	
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "timesheet_id",referencedColumnName ="id")
//	Timesheet timesheet;
//	
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "performance_review_id",referencedColumnName ="id")
//	PerformanceReview performanceReview;
	
	
}
