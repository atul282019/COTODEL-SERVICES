package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.entity.UserWaitingListEntity;

@Repository
public interface UserWaitingListRepository extends JpaRepository<UserWaitingListEntity, Long> {
	
	@Query("select s  from UserWaitingListEntity s where s.email = ?1")
	public UserWaitingListEntity getByUser(String userEmail);
	
	@Query("select s  from UserWaitingListEntity s order by s.id desc")
	public List<UserWaitingListEntity> getByUserList();
} 

