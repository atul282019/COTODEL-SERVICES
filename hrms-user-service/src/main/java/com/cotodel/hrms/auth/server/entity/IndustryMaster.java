package com.cotodel.hrms.auth.server.entity;

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
@Table(name="industry_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="industry_master_seq" , sequenceName="industry_master_seq", allocationSize=1)
public class IndustryMaster implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="industry_master_seq")
	@Column(name="indus_code")
	private Long indusCode;
	
	@Column(name="industory_name")
	private String industoryName;

}
