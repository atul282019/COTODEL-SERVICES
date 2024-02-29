package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.LeaveRequestDao;
import com.cotodel.hrms.auth.server.model.LeaveRequest;
import com.cotodel.hrms.auth.server.repository.LeaveRequestRepository;
@Repository
public class LeaveRequestDaoImpl implements LeaveRequestDao{

	@Autowired
	LeaveRequestRepository leaveRequestRepository;
	
	@Override
	public LeaveRequest saveDetails(LeaveRequest leaveRequestEntity) {
		// TODO Auto-generated method stub
		return leaveRequestRepository.saveAndFlush(leaveRequestEntity);
	}

	@Override
	public LeaveRequest getLeaveRequest(Long employeeId) {
		// TODO Auto-generated method stub
		return leaveRequestRepository.getByLeave(employeeId);
	}

}
