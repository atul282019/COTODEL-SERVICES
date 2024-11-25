package com.cotodel.hrms.auth.server.service;

import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadSFListResponse;

@Service
public interface ErupiVoucherCreationBulkService {
	public ErupiVoucherBulkUploadSFListResponse saveErupiVoucherBulkFile(ErupiVoucherBulkUploadRequest erupiVoucherBulkUploadRequest);
}
