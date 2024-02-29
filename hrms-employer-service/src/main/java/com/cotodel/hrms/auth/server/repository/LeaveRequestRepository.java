package com.cotodel.hrms.auth.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.LeaveRequest;
@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>{
	@Query("select s  from LeaveRequest s where s.employeeId = ?1")
	public LeaveRequest getByLeave(Long empid);
}
