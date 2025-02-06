package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;

public interface AdvanceTravelRequestDao {
	public AdvanceTravelRequestEntity saveDetails(AdvanceTravelRequestEntity advanceTravelRequestEntity);
	public List<AdvanceTravelRequestEntity> findByEmployerId(Long employerId);
	public List<AdvanceTravelRequestEntity> findByEmployeeId(Long employeeId);
	public AdvanceTravelRequestEntity findById(Long id);
	public List<AdvanceTravelRequestEntity> findByEmployerId(Long employerId,int status);
	public List<AdvanceTravelRequestEntity> findByEmployeeId(Long employeeId,int status);
	public void deleteById(Long id);
	public List<AdvanceTravelRequestEntity> findApprovalByEmployerId(Long employerId);
}
