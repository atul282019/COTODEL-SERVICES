package com.cotodel.hrms.auth.server.repository.bulk;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadSuccessEntity;

public interface VoucherBulkUploadSuccessRepository extends JpaRepository<VoucherBulkUploadSuccessEntity, Long>{

	@Query("select s  from VoucherBulkUploadSuccessEntity s where s.orgId = :orgId and s.fileName=:fileName")
	public List<VoucherBulkUploadSuccessEntity> findByOrgIdAndFileName(@Param("orgId") Long orgId,
		    @Param("fileName") String fileName);
}
