package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
	  
	  @Query("select c from UserEntity c where c.mapperFlag='Y' and ((c.employerid = ?1) or (c.id=?1and c.role_id='1') )")
	  List<UserEntity> findByUserList(int employerid);
	  
	  @Query("select c from UserEntity c where (c.mapperFlag='N' OR c.mapperFlag IS NULL) and LOWER(c.username) LIKE LOWER(CONCAT('%', ?2, '%')) and ((c.employerid = ?1) or (c.id=?1and c.role_id='1')) ")
	  List<UserEntity> findByUserSearchList(int orgId,String userName); 
	  
	    @Modifying
	    @Transactional
	    @Query(value = "UPDATE user_details  SET mapper_flag=:mapperFlag WHERE mobile =:mobile", nativeQuery = true)
	    public int updateMapperFlag(@Param("mobile") String mobile,@Param("mapperFlag") String mapperFlag);
	  
} 

