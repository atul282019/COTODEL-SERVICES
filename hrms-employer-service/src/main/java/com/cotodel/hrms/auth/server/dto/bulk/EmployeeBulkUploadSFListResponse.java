package com.cotodel.hrms.auth.server.dto.bulk;

import java.util.List;

import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadSuccessEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBulkUploadSFListResponse {
	
	private String totalCount;
	private String successCount;
	private String failCount;
	List<EmployeeBulkUploadSuccessEntity> success;
	List<EmployeeBulkUploadFailEntity> fail;
	private String response;
	
}
