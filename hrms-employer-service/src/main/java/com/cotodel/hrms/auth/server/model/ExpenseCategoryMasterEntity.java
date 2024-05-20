package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;

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
@Table(name="expense_category_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="expense_category_master_seq" , sequenceName="expense_category_master_seq", allocationSize=1)

public class ExpenseCategoryMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="expense_category_master_seq")	
	@Column(name="id")
	private Long id;	
    
	@Column(name="expense_category")
	private String expenseCategory;	
	
	@Column(name="expense_code")
	private String expenseCode;
	
	@Column(name="expense_limit")
	private String expenseLimit;
	
	@Column(name="day_to_expiry")
	private String dayToExpiry;
	
	@Column(name="status")
	private long status;
	
}
