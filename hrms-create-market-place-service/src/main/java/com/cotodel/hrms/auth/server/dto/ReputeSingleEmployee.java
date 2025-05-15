package com.cotodel.hrms.auth.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputeSingleEmployee {
	List<ReputeEmployee> employeeList;
	private int totalElements;
	private int size;
	private boolean hasNext;
	private boolean hasPrevious;
	private String sortBy;
	private String sortOrder;
}
