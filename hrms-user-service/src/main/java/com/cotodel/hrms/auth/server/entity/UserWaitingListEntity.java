package com.cotodel.hrms.auth.server.entity;


import java.io.Serializable;
import java.time.LocalDate;

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
	
    private String name;
    @Column(unique=true)
    private String email ;
    private LocalDate  created_date ;
}
