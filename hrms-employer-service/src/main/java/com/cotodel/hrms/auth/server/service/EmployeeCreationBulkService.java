package com.cotodel.hrms.auth.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkCreateRequest;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.bulk.EmployeeBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiBulkIdRequest;

@Service
public interface EmployeeCreationBulkService {
	public EmployeeBulkUploadSFListResponse saveEmployeeBulkFileUpload(EmployeeBulkUploadRequest employeeBulkUploadRequest);
	public List<EmployeeOnboardingRequest> createErupiEmployeeBulkFile(EmployeeBulkCreateRequest erupiVoucherBulkUploadRequest);
	//public int updateEmployeeStatus(ErupiBulkIdRequest eBulkIdRequest);
	
	public EmployeeBulkCreateRequest createErupiVoucherBulkFileHash(EmployeeBulkCreateRequest erupiVoucherBulkUploadRequest);
}
