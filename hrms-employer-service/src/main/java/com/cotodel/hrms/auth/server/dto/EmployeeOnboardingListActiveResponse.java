package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingListActiveResponse {
	
	private String total;
	private String active;
	private List<EmployeeOnboardingEntity> empList;
	
}
