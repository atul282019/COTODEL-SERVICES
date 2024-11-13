package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.VoucherTypeDto;
import com.cotodel.hrms.auth.server.model.VoucherTypeMasterEntity;
@Repository
public interface VoucherTypeMasterRepository extends JpaRepository<VoucherTypeMasterEntity,Long>{
		
	@Query("SELECT new com.cotodel.hrms.auth.server.dto.VoucherTypeDto(u.voucherCode, u.voucherDesc) FROM VoucherTypeMasterEntity u where u.status='1' ")
    List<VoucherTypeDto> findAllUserDTOs();
	
	@Query("SELECT s FROM VoucherTypeMasterEntity s where s.voucherCode = ?1 and s.status='1' ")
    VoucherTypeMasterEntity findVoucherTypeMasterDetail(String voucherCode);
	
	@Query("SELECT s FROM VoucherTypeMasterEntity s where  s.activeStatus='1' ")
    List<VoucherTypeMasterEntity> findVoucherTypeMasterList();
}
