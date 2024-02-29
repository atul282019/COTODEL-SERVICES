package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.Timesheet;

public interface TimesheetDao {
	public Timesheet saveDetails(Timesheet timesheet);
	public Timesheet getTimesheet(Long employeeId);
}
