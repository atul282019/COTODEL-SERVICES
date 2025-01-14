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

//@Data
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name="emergency_contacts")
//@Access(value=AccessType.FIELD)
//@SequenceGenerator(name="emergency_contacts_seq" , sequenceName="emergency_contacts_seq", allocationSize=1)
//public class EmergencyContacts implements Serializable{
//	
//	private static final long serialVersionUID = 4615208660281419839L;
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="emergency_contacts_seq")	
//	@Column(name="id")
//	private Long id;
//	
//	private String name;
//	private String relationship;
//	private String phone;
//	
//}
