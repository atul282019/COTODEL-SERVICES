package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.UserDto;
import com.cotodel.hrms.auth.server.entity.SignUpEntity;

@Repository
public interface SignUpRepository extends JpaRepository<SignUpEntity, Long> {
	
	@Query("select new com.cotodel.hrms.auth.server.dto.UserDto(c.email,c.mobile,c.username,case when c.status='1' then 'ACTIVE' else 'IN-ACTIVE' End) from UserEntity c where (c.employerid = ?1 and c.role_id='2')")
	List<UserDto> findByEmployerId(int employerid);
} 

