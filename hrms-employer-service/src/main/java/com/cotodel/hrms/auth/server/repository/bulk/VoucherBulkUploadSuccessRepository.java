package com.cotodel.hrms.auth.server.repository.bulk;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadSuccessEntity;

public interface VoucherBulkUploadSuccessRepository extends JpaRepository<VoucherBulkUploadSuccessEntity, Long>{

	@Query("select s  from VoucherBulkUploadSuccessEntity s where s.orgId = :orgId and s.fileName=:fileName")
	public List<VoucherBulkUploadSuccessEntity> findByOrgIdAndFileName(@Param("orgId") Long orgId,
		    @Param("fileName") String fileName);
	
	@Query("select s  from VoucherBulkUploadSuccessEntity s where s.id =:id and s.createFlag='N' and s.status='1' ")
	public VoucherBulkUploadSuccessEntity findByIdWithFlagN(@Param("id") Long id);
	
	
	@Modifying
    @Transactional
    @Query(value = "UPDATE voucher_bulk_upload_success  SET create_flag ='Y' WHERE id =:id", nativeQuery = true)
    public int updateSuccessFlag(@Param("id") Long id);
	
	@Modifying
    @Transactional
    @Query(value = "UPDATE voucher_bulk_upload_success  SET status ='0' WHERE id =:id", nativeQuery = true)
    public int updateSuccessStatus(@Param("id") Long id);
}
