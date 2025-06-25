package com.cotodel.hrms.auth.server.repository.bulk;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherMasterUploadEntity;

public interface VoucherMasterUploadRepository extends JpaRepository<VoucherMasterUploadEntity, Long>{

}
