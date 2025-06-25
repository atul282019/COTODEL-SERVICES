package com.cotodel.hrms.auth.server.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExpanceTravelAdvance {
	

	private Long id;	
    private Long employerId;	
    private String allowEmployeesTravel;	
    private String allowEmployeesCash;	
    private String employeesAllow;	
    private List<String> nameEmployeesCash;	
    private String daysDisbursalCash;
    private LocalDate  modified_date ;	
    private Long status;
}
