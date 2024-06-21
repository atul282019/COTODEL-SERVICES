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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="category_emp_band")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="category_emp_band_seq" , sequenceName="category_emp_band_seq", allocationSize=1)

public class CategoryEmployeeBandEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="category_emp_band_seq")	
	@Column(name="id")
	private Long id;	
    
	@Column(name="band_type")
	private String bandType;	
	
	@Column(name="band_one_inr")
	private int bandOneInr;
	
	@Column(name="band_two_inr")
	private int bandTwoInr;
	
	@Column(name="band_three_inr")
	private int bandThreeInr;
	
	@Column(name="band_four_inr")
	private int bandFourInr;
	
	@Column(name="band_five_inr")
	private int bandFiveInr;
	
	@Column(name="band_six_inr")
	private int bandSixInr;
	
	@Column(name="expense_category_id")
	private Long expenseCategoryId;
	

}
