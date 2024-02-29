package com.cotodel.hrms.auth.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.Timesheet;
@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long>{
	@Query("select s  from Timesheet s where s.employeeId = ?1")
	public Timesheet getByTimesheet(Long empid);
}
