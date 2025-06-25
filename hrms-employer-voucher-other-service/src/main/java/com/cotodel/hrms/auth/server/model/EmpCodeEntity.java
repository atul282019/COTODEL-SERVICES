package com.cotodel.hrms.auth.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class EmpCodeEntity {


	 	@Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_code_seq")
	    @SequenceGenerator(name = "emp_code_seq", sequenceName = "empcode", allocationSize = 1)
	    private Long id;
	private String name;
}
