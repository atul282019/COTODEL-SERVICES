package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.TimesheetDao;
import com.cotodel.hrms.auth.server.model.Timesheet;
import com.cotodel.hrms.auth.server.repository.TimesheetRepository;
@Repository
public class TimesheetDaoImpl implements TimesheetDao{

	@Autowired
	TimesheetRepository timesheetRepository;

	@Override
	public Timesheet saveDetails(Timesheet timesheet) {
		// TODO Auto-generated method stub
		return timesheetRepository.saveAndFlush(timesheet);
	}

	@Override
	public Timesheet getTimesheet(Long employeeId) {
		// TODO Auto-generated method stub
		return timesheetRepository.getByTimesheet(employeeId);
	}


}
