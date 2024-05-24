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
@Table(name="employee_band_add_tier")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="employee_band_add_tier_seq" , sequenceName="employee_band_add_tier_seq", allocationSize=1)

public class EmployeeBandAddTierEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_band_add_tier_seq")	
	@Column(name="id")
	private Long id;	
		
	
	@Column(name="employee_band_id")
	private Long employeeBandId;
	
	@Column(name="employee_band")
	private String employeeBand;	
	
	@Column(name="additional_tiers")
	private int additionalTiers;		
	
	@Column(name="additional_tiers_one")
	private String additionalTiersOne;
	
	@Column(name="additional_tiers_two")
	private String additionalTiersTwo;
	
	@Column(name="additional_tiers_three")
	private String additionalTiersThree;
	
	@Column(name="additional_tiers_four")
	private String additionalTiersFour;
	
	@Column(name="additional_tiers_five")
	private String additionalTiersFive;
}
