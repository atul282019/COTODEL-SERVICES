package com.cotodel.hrms.auth.server.repository;

import java.util.List;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
import com.cotodel.hrms.auth.server.entity.UserEntity;

@Repository
public interface EmployerDetailsRepository extends JpaRepository<EmployerDetailsEntity, Long> {
	@Query("select s from EmployerDetailsEntity s where s.id =:orgId  ")
	public EmployerDetailsEntity getEmployerDetailsByEmpId(@Param("orgId") Long orgId);
	
	@Query("select s from EmployerDetailsEntity s where s.id =:id  ")
	public EmployerDetailsEntity getEmployerDetailsById(@Param("id") Long id);
	
	@Query("select s from EmployerDetailsEntity s where s.companyId =:companyId and s.hrmsId=:hrmsId ")
	public EmployerDetailsEntity getEmployerDetailsByCompId(@Param("companyId") String companyId,@Param("hrmsId") String hrmsId);
	
	@Query("select s from EmployerDetailsEntity s where (s.organizationName =:organizationName  or s.mobile =:mobile)")
	List<EmployerDetailsEntity> checkOrgAndMobile(@Param("organizationName") String organizationName,@Param("mobile") String mobile);

}
