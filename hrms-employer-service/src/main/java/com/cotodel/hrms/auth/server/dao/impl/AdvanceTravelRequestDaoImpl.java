package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.AdvanceTravelRequestDao;
import com.cotodel.hrms.auth.server.model.AdvanceTravelRequestEntity;
import com.cotodel.hrms.auth.server.repository.AdvanceTravelRequestRepository;
@Repository

public class AdvanceTravelRequestDaoImpl implements AdvanceTravelRequestDao{

	@Autowired
	AdvanceTravelRequestRepository advanceTravelRequestRepository;

	@Override
	public AdvanceTravelRequestEntity saveDetails(AdvanceTravelRequestEntity advanceTravelRequestEntity) {
		// TODO Auto-generated method stub
		return advanceTravelRequestRepository.saveAndFlush(advanceTravelRequestEntity);
	}

	@Override
	public List<AdvanceTravelRequestEntity> findByEmployerId(Long employerId) {
		
		return advanceTravelRequestRepository.findEmployerId(employerId);
	}

	@Override
	public List<AdvanceTravelRequestEntity> findByEmployeeId(Long employeeId) {
		// TODO Auto-generated method stub
		return advanceTravelRequestRepository.findEmployeeId(employeeId);
	}

	@Override
	public AdvanceTravelRequestEntity findById(Long id) {
		// TODO Auto-generated method stub
		return advanceTravelRequestRepository.getById(id);
	}

	@Override
	public List<AdvanceTravelRequestEntity> findByEmployerId(Long employerId, int status) {
		// TODO Auto-generated method stub
		return advanceTravelRequestRepository.findEmployerId(employerId,status);
	}

	@Override
	public List<AdvanceTravelRequestEntity> findByEmployeeId(Long employeeId, int status) {
		// TODO Auto-generated method stub
		return advanceTravelRequestRepository.findEmployeeId(employeeId,status);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		advanceTravelRequestRepository.deleteDetails(id);
	}

	@Override
	public List<AdvanceTravelRequestEntity> findApprovalByEmployerId(Long employerId) {
		// TODO Auto-generated method stub
		return advanceTravelRequestRepository.findApprovalEmployerId(employerId);
	}
	
}
