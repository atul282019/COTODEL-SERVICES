package com.cotodel.hrms.auth.server.repository.bulk;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadEntity;

public interface VoucherBulkUploadRepository extends JpaRepository<VoucherBulkUploadEntity, Long>{

}
