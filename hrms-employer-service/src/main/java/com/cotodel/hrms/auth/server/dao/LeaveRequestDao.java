package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.model.LeaveRequest;

public interface LeaveRequestDao {
	public LeaveRequest saveDetails(LeaveRequest leaveRequestEntity);
	public LeaveRequest getLeaveRequest(Long employeeId);
}
