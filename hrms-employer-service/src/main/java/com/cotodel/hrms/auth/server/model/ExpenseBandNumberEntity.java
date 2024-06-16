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
@Table(name="expense_category_number")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="expense_category_number_seq" , sequenceName="expense_category_number_seq", allocationSize=1)

public class ExpenseBandNumberEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="expense_category_number_seq")	
	@Column(name="id")
	private Long id;	
    
	@Column(name = "employer_id", unique = true)
	private long employerId;	
	
	@Column(name="status")
	private long status;
	@Column(name = "band_name_one")
	private String BandNameOne;
	@Column(name = "band_name_two")
	private String BandNameTwo;
	@Column(name = "band_name_three")
	private String BandNameThree;
	@Column(name = "band_name_four")
	private String BandNameFour;
	@Column(name = "band_name_five")
	private String BandNameFive;
	@Column(name = "band_name_six")
	private String BandNameSix;
}
