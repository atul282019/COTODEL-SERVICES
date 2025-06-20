package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadSuccessEntity;

public interface EmployeeBulkDao {
	public EmployeeBulkUploadEntity saveDetails(EmployeeBulkUploadEntity erBulkUploadEntity);
	public EmployeeBulkUploadSuccessEntity saveSuccessDetails(EmployeeBulkUploadSuccessEntity emplBulkUploadEntity);
	public EmployeeBulkUploadFailEntity saveFailDetails(EmployeeBulkUploadFailEntity erBulkUploadEntity);
	public List<EmployeeBulkUploadSuccessEntity> findSuccessList(Long orgId, String fileName);
	public List<EmployeeBulkUploadFailEntity> findFailList(Long orgId, String fileName);
	public EmployeeBulkUploadSuccessEntity findSuccessDetails(Long id);
	public int updateSuccessFlag(Long id);
//	public int updateSuccessStatus(Long id);
//	public VoucherMasterUploadEntity saveDetails(VoucherMasterUploadEntity voucherMasterUploadEntity);
	
}
