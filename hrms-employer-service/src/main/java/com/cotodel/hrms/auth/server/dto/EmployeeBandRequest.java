package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeBandRequest {
	
	private String bandId;
	private String bandPerDay;
	private String bandPerMonth;
	private String response;
}
