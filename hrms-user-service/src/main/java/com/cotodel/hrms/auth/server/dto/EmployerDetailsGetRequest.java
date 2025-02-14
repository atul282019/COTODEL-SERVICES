package com.cotodel.hrms.auth.server.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDetailsGetRequest {
	@NotNull(message = "Employer id cannot be null")
    @Min(value = 1, message = "Employer Id must be greater than or equal to 1")
	private Long employerId;
	private Long employeeId;
	private String gstnNo;
	private String organizationType;
	private String pan;
	private String brandName;
	private String panDetails;
	private String companyName;
	private String officeAddress;
	private String addressLine;
	private String pinCode;
	private String stateCode;
	private String payrollEnabledFlag;
	private String paidDate;
	private String runPayrollFlag;
	private String salaryAdvancesFlag;
	private Long id;
	private String orgType;
	private String orgsubType;
	private String orgDesc;
	private int status;
	private int orgTypeId;
	private String gst;
	private String profileComplete;
}
