package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.UserManagerDto;
import com.cotodel.hrms.auth.server.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	

	  @Query("select s  from UserEntity s where s.username = ?1 and s.status=1")
	  public UserEntity getByUser(String userName);
	  
	  @Query("select s  from UserEntity s where s.username = ?1")
	  public UserEntity checkUserDetails(String userName);
	  
	  @Query("select s  from UserEntity s where s.mobile = ?1")
		public UserEntity checkUserMobile(String userMobile);
	  
	  @Query("select s  from UserEntity s where s.email = ?1")
		public UserEntity checkUserEmail(String userEmail);
	 
	  @Query("select c from UserEntity c where (c.mobile = ?1 or c.email = ?2 or c.username=?3)")
		List<UserEntity> findByMobileAndEmailAndStatus(String mobile,String email,String username);
	  
	  @Query("select c from UserEntity c where (c.mobile = ?1 or c.email = ?2 or c.username=?3)")
		UserEntity findByMobileAndEmail(String mobile,String email,String username);
	  
	  @Query("select c from UserEntity c where c.id = ?1")
		UserEntity findByEmployerExist(Long id);
	  
	  @Query("select c from UserEntity c where (c.mobile = ?1 or c.email = ?2  and c.role_id='1')")
	   UserEntity findUserByMobileAndEmail(String mobile,String email);
	  
	  @Query("select c from UserEntity c where c.mapperFlag='Y' and ((c.employerid = ?1) or (c.id=?1and c.role_id='1') ) and c.mobile not in (?2)")
	  List<UserEntity> findByUserList(int employerid,String mobile);
	  
	  @Query("select c from UserEntity c where (c.mapperFlag='N' OR c.mapperFlag IS NULL) and c.mobile not in (?3) and LOWER(c.username) LIKE LOWER(CONCAT('%', ?2, '%')) and ((c.employerid = ?1) or (c.id=?1and c.role_id='1')) ")
	  List<UserEntity> findByUserSearchList(int orgId,String userName,String mobile); 
	  
	    @Modifying
	    @Transactional
	    @Query(value = "UPDATE user_details  SET mapper_flag=:mapperFlag WHERE mobile =:mobile", nativeQuery = true)
	    public int updateMapperFlag(@Param("mobile") String mobile,@Param("mapperFlag") String mapperFlag);
	    
	    @Query("select new com.cotodel.hrms.auth.server.dto.UserManagerDto(c.id,c.email,c.mobile,c.username) from UserEntity c where  (c.employerid = ?1 and c.id <>?2 and c.status=1)  ")
		List<UserManagerDto> getUserManagerList(int orgId,Long employeeId);
	    
	    @Query("select new com.cotodel.hrms.auth.server.dto.UserManagerDto(c.id,c.email,c.mobile,c.username) from UserEntity c where  (c.employerid = ?1 and c.status=1 )  ")
		List<UserManagerDto> getUserManagerList(int orgId);
	    
	    @Query("select c from UserEntity c where   c.mobile=:mobile and c.role_id in ('1','9','10','12') ")
		UserEntity userEligible(@Param("mobile") String mobile);
	    
	    @Query("select c from UserEntity c where  LOWER(c.username) LIKE LOWER(CONCAT('%', :userName, '%')) and ((c.employerid =:orgId) or (c.id=:orgId and c.role_id='1') or (c.id=:orgId and c.role_id='9')) ")
		List<UserEntity> findByUserSearchWithoutMobile(@Param("orgId") int orgId,@Param("userName") String userName);
	    
	    @Query("select s  from UserEntity s where s.mobile =?2 and (s.employerid=?1  or s.id=?1 )")
		public UserEntity checkUserMobileAndOrgId( int orgId,String mobile);
	    
	    @Query("select s  from UserEntity s where s.companyId =?1 and (s.hrmsId=?2 and role_id='3' )")
		public UserEntity checkUserCompAndHrmsId(String companyId, String hrmsId);
	    
	    @Modifying
	    @Transactional
	    @Query(value = "UPDATE user_details  SET status=:status WHERE mobile =:mobile", nativeQuery = true)
	    public int updateActiveDeactive(@Param("mobile") String mobile,@Param("status") int status);
	    
	    
	    @Query("select s  from UserEntity s where s.mobile =:mobile and s.employerid=:orgId and s.status='1' ")
		public UserEntity checkUserMobileAndOrgIdStatus(@Param("orgId") int orgId,@Param("mobile") String mobile);
} 

