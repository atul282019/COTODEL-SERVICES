package com.cotodel.hrms.auth.server.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name="user_waiting_list")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="user_waiting_list_seq" , sequenceName="user_waiting_list_seq", allocationSize=1)
public class UserWaitingListEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_waiting_list_seq")
	private Long id ;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="company_size")
	private String companySize;
	
	@Column(name="industry")
	private String industry;
	
	@Column(name="contact_person_name")
	private String contactPersonName;
	
	@Column(name="contact_number")
	private String contactNumber;
    
	@Column(name="created_date")
    private LocalDateTime  createdDate ;
	
	@Column(name="email")
	private String email;
	
	@Column(name="status")
	private int status;
	
	@Column(name="status_remarks")
	private String statusRemarks;
	
	@Column(name="erupi_status")
	private boolean erupistatus;
					
	
	
}
