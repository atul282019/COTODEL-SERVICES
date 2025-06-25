package com.cotodel.hrms.auth.server.repository.bulk;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadFailEntity;

public interface EmployeeBulkUploadFailRepository extends JpaRepository<EmployeeBulkUploadFailEntity, Long>{
	@Query("select s  from EmployeeBulkUploadFailEntity s where s.orgId =:orgId and s.fileName=:fileName ")
	public List<EmployeeBulkUploadFailEntity> findByOrgIdAndFileName(@Param("orgId") Long orgId,
		    @Param("fileName") String fileName);
}
