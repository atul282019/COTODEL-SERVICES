package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.VoucherTypeDto;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
@Repository
public interface VoucherTypeMasterRepository extends JpaRepository<VoucherTypeMasterEntity,Long>{
		
	@Query("SELECT new com.cotodel.hrms.auth.server.dto.VoucherTypeDto(u.voucherCode, u.voucherDesc) FROM VoucherTypeMasterEntity u where u.status='1' ")
    List<VoucherTypeDto> findAllUserDTOs();
	
	@Query("SELECT s FROM VoucherTypeMasterEntity s where s.voucherCode = ?1 and s.status='1' ")
    VoucherTypeMasterEntity findVoucherTypeMasterDetail(String voucherCode);
	
	@Query("SELECT s FROM VoucherTypeMasterEntity s  ")
    List<VoucherTypeMasterEntity> findVoucherTypeMasterList();
	
	@Modifying
    @Transactional
    @Query(value = "UPDATE voucher_type_master  SET active_status =:activeStatus,status=:status WHERE id_pk =:id", nativeQuery = true)
    public int updateActiveStatus(@Param("id") Long id,@Param("activeStatus") String activeStatus,@Param("status") Long status);
}
