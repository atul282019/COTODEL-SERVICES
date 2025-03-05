package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.RoleDto;
import com.cotodel.hrms.auth.server.dto.UserRoleMapperDto;
import com.cotodel.hrms.auth.server.entity.RoleMasterEntity;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperEntity;

@Repository
public interface UserRoleMapperRepository extends JpaRepository<UserRoleMapperEntity, Long> {
	
	@Query("select new com.cotodel.hrms.auth.server.dto.UserRoleMapperDto( s.id,s.mobile,s.roleId,s.orgId,s.creationDate,s.createdBy,s.status,s.statusDesc, "
			+ "r.roleDesc)  from UserRoleMapperEntity s join RoleMasterEntity r on s.roleId=r.roleId where s.mobile = ?1")
	  public List<UserRoleMapperDto> getByMobile(String mobile);
	
	@Query("select s from UserRoleMapperEntity s where s.mobile =:mobile and s.orgId=:orgId and s.roleId=:roleId")
	  public UserRoleMapperEntity getMapperMobileValue(@Param("mobile") String mobile, @Param("orgId") Long orgId, @Param("roleId") int roleId);
	
	@Query("select s from UserRoleMapperEntity s where s.mobile =:mobile and s.orgId=:orgId")
	  public List<UserRoleMapperEntity> getMapperMobileandOrgValue(@Param("mobile") String mobile, @Param("orgId") Long orgId);
	
//	@Query("delete from UserRoleMapperEntity s where s.mobile =:mobile and s.orgId=:orgId")
//	  public void getMapperMobilDelete(@Param("mobile") String mobile, @Param("orgId") Long orgId);
	
	 	@Modifying
	    @Transactional
	    @Query("DELETE FROM UserRoleMapperEntity s WHERE s.mobile = :mobile AND s.orgId = :orgId")
	    void getMapperMobilDelete(@Param("mobile") String mobile, @Param("orgId") Long orgId);

	 	//role_id in ('13','9','10','12')
	 	
	 	@Query("SELECT new com.cotodel.hrms.auth.server.dto.RoleDto(r.roleId, r.roleDesc) " +
	 	       "FROM RoleMasterEntity r WHERE r.roleId in ('13','9','10','12') AND r.roleId NOT IN " +
	 	       "(SELECT urm.roleId FROM UserRoleMapperEntity urm WHERE urm.mobile = :mobile)")
	 	public List<RoleDto> getByMobileRoleMaster(@Param("mobile") String mobile);
} 
