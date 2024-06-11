package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import com.cotodel.hrms.auth.server.model.CategoryEmployeeBandEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTravelAdvanceRequest {
	
	private Long id;
    private Long employerId;	
    private String allowEmployeesTravel;	
    private String allowEmployeesCash;	
    private String employeesAllow;	
    private String nameEmployeesCash;	
    private String daysDisbursalCash;	
	private LocalDate  created_date ;
	private String response;
}
