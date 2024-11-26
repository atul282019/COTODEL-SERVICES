package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadSuccessEntity;

public interface ErupiVoucherBulkDao {
	public VoucherBulkUploadEntity saveDetails(VoucherBulkUploadEntity erBulkUploadEntity);
	public VoucherBulkUploadSuccessEntity saveSuccessDetails(VoucherBulkUploadSuccessEntity erBulkUploadEntity);
	public VoucherBulkUploadFailEntity saveFailDetails(VoucherBulkUploadFailEntity erBulkUploadEntity);
	public List<VoucherBulkUploadSuccessEntity> findSuccessList(Long orgId, String fileName);
	public List<VoucherBulkUploadFailEntity> findFailList(Long orgId, String fileName);
	public VoucherBulkUploadSuccessEntity findSuccessDetails(Long id);
	public int updateSuccessFlag(Long id);
	public int updateSuccessStatus(Long id);
	
}
