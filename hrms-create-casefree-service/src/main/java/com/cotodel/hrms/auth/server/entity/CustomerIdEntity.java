package com.cotodel.hrms.auth.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class CustomerIdEntity {


	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerid_seq")
	    @SequenceGenerator(name = "customerid_seq", sequenceName = "customerid", allocationSize = 1)
	    private Long id;
	private String name;
}
