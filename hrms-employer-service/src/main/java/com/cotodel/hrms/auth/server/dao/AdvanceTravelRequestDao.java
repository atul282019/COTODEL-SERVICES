package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.AdvanceTravelDto;
import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;

public interface AdvanceTravelRequestDao {
	public AdvanceTravelRequestEntity saveDetails(AdvanceTravelRequestEntity advanceTravelRequestEntity);
	public List<AdvanceTravelDto> findByEmployerId(Long employerId);
	public List<AdvanceTravelDto> findByEmployeeId(Long employeeId);
	public AdvanceTravelRequestEntity findById(Long id);
	public List<AdvanceTravelRequestEntity> findByEmployerId(Long employerId,int status);
	public List<AdvanceTravelRequestEntity> findByEmployeeId(Long employeeId,int status);
	public void deleteById(Long id);
	public List<AdvanceTravelDto> findApprovalByEmployerId(Long employerId);
	public List<AdvanceTravelRequestEntity> findByEmployerSequenceId(Long employerId,String sequenceId);
	public List<AdvanceTravelRequestEntity> findByEmployeeSequenceId(Long employeeId,String sequenceId);
	public List<AdvanceTravelRequestEntity> findByEmployerIdDraft(Long employerId);
	public List<AdvanceTravelRequestEntity> findByEmployeeIdDraft(Long employeeId);
}
