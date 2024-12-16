package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.UserRoleMapperDto;
import com.cotodel.hrms.auth.server.entity.UserRoleMapperEntity;

@Repository
public interface UserRoleMapperRepository extends JpaRepository<UserRoleMapperEntity, Long> {
	
	@Query("select new com.cotodel.hrms.auth.server.dto.UserRoleMapperDto( s.id,s.mobile,s.roleId,s.orgId,s.creationDate,s.createdBy,s.status,s.statusDesc, "
			+ "r.roleDesc)  from UserRoleMapperEntity s join RoleMasterEntity r on s.roleId=r.roleId where s.mobile = ?1")
	  public List<UserRoleMapperDto> getByMobile(String mobile);
	
	@Query("select s from UserRoleMapperEntity s where s.mobile =:mobile and s.orgId=:orgId and s.roleId=:roleId")
	  public UserRoleMapperEntity getMapperMobileValue(@Param("mobile") String mobile, @Param("orgId") Long orgId, @Param("roleId") int roleId);
	

} 
