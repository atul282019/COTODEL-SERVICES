package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
@Table(name="performance_review")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="performance_review_seq" , sequenceName="performance_review_seq", allocationSize=1)
public class PerformanceReview implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="performance_review_seq")	
	@Column(name="id")
	private Long id;

//	@OneToOne
//    @JoinColumn(name = "company_employee_id")
//    private CompanyEmployeeEntity employee;
	
	
	@Column(name = "company_employee_id")
    private Long employeeId;
	
	@Column(name="review_date")
	private LocalDate reviewDate;
	
	private String comments;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ratings_id",referencedColumnName ="id")
	Ratings ratings;
		
}
