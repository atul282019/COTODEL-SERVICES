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
@Table(name="performance_review")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="performance_review_seq" , sequenceName="performance_review_seq", allocationSize=1)
public class PerformanceReviewEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="performance_review_seq")
	@Column(name="review_id")
	private Long reviewId;
	
	@OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
	
	@OneToOne
    @JoinColumn(name = "employer_id")
    private EmployerEntity employer;
	
	@CreationTimestamp
	@Column(name="review_date")
	private Date reviewDate;
	
	private String rating;
	private String comments;
	
}
