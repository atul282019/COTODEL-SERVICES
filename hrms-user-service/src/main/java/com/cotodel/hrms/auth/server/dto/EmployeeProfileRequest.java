package com.cotodel.hrms.auth.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileRequest {
	
	private String organization_type;
    private String pan;
    private String brand_name;
    private String pan_details;
    private String company_name;
    private String office_address;
    private String address_line;
    private String pincode;
    private String state_code;
    private String payroll_enabled_flag;
    private String paid_date;
    private String run_payroll_flag;
    private String salary_advances_flag;
     
     
}
