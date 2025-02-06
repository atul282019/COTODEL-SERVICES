package com.cotodel.hrms.auth.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
@Entity
public class EmployerCodeEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employer_code_seq")
    @SequenceGenerator(name = "employer_code_seq", sequenceName = "employercode", allocationSize = 1)
    private Long id;
	private String name;
}
