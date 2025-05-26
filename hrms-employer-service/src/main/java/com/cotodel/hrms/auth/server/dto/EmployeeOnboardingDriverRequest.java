package com.cotodel.hrms.auth.server.dto;

import java.io.Serializable;

import com.cotodel.hrms.auth.server.sql.NoSqlKeywords;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoSqlKeywords
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingDriverRequest implements Serializable {
    private static final long serialVersionUID = 1L;
   	private Long orgId;
   	private String name;
}
