package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="expense_category" , uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"expense_code", "employer_id"})})
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="expense_category_seq" , sequenceName="expense_category_seq", allocationSize=1)

public class ExpenseCategoryBandEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="expense_category_seq")	
	@Column(name="id")
	private Long id;	
    
	@Column(name="expense_category")
	private String expenseCategory;	
	
	@Column(name="band_flag")
	private String bandFlag;
	
	@Column(name="band_id")
	private String bandId;
	
	@Column(name="band_per_day")
	private String bandPerDay;
	
	@Column(name="band_per_month")
	private String bandPerMonth;
	
	@Column(name="day_to_expiry")
	private String dayToExpiry;
	
	@Column(name = "employer_id")
	private long employerId;
	
	@Column(name="expense_code")
	private String expenseCode;
	
	@Column(name="status")
	private long status;	
	
}
