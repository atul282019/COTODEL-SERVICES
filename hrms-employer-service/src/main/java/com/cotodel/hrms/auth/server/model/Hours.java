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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="hours")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="hours_seq" , sequenceName="hours_seq", allocationSize=1)
public class Hours implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hours_seq")	
	@Column(name="id")
	private Long id;
	
	private String project;
	private Long hour;
	
	
}
