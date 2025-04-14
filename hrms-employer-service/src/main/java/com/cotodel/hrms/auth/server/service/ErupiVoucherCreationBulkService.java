package com.cotodel.hrms.auth.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateDetailsRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiBulkIdRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadRequest;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkUploadSFListResponse;
import com.cotodel.hrms.auth.server.dto.bulk.ErupiVoucherBulkVoucherCreateRequest;

@Service
public interface ErupiVoucherCreationBulkService {
	//public ErupiVoucherBulkUploadSFListResponse saveErupiVoucherBulkFile(ErupiVoucherBulkUploadRequest erupiVoucherBulkUploadRequest);
	public List<ErupiVoucherCreateDetailsRequest> createErupiVoucherBulkFile(ErupiVoucherBulkVoucherCreateRequest erupiVoucherBulkUploadRequest);
	public int updateErupiVoucherStatus(ErupiBulkIdRequest eBulkIdRequest);
	//public ErupiVoucherMasterUploadSFResponse saveErupiVoucherMasterFile(ErupiVoucherMasterUploadRequest erupiVoucherBulkUploadRequest);
	
	public ErupiVoucherBulkUploadSFListResponse saveErupiVoucherBulkFileNew(ErupiVoucherBulkUploadRequest erupiVoucherBulkUploadRequest);
	public ErupiVoucherBulkVoucherCreateRequest createErupiVoucherBulkFileHash(ErupiVoucherBulkVoucherCreateRequest erupiVoucherBulkUploadRequest);
}
