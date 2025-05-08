package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherRequestDao;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;
import com.cotodel.hrms.auth.server.repository.ErupiVoucherRequestRepository;
@Repository
public class ErupiVoucherRequestDaoImpl implements ErupiVoucherRequestDao{

	@Autowired
	ErupiVoucherRequestRepository erupiVoucherRequestRepository;
	
	

	@Override
	public ErupiVoucherCreationRequestEntity saveDetails(ErupiVoucherCreationRequestEntity erupiVoucherCreationRequestEntity) {
		// TODO Auto-generated method stub
		return erupiVoucherRequestRepository.saveAndFlush(erupiVoucherCreationRequestEntity);
	}



	@Override
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequest(Long employerId) {
		// TODO Auto-generated method stub
		return erupiVoucherRequestRepository.findByEmployerId(employerId);
	}



	@Override
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequestEmp(Long employeeId) {
		// TODO Auto-generated method stub
		return erupiVoucherRequestRepository.findByEmployeeId(employeeId);
	}



	@Override
	public ErupiVoucherCreationRequestEntity getVoucherCreationRequestEmpById(Long id) {
		// TODO Auto-generated method stub
		return erupiVoucherRequestRepository.getById(id);
	}



	@Override
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequestApproved(Long employerId) {
		// TODO Auto-generated method stub
		return erupiVoucherRequestRepository.findByApprovedEmployerId(employerId);
	}



	@Override
	public List<ErupiVoucherCreationRequestEntity> getVoucherCreationRequestApprovedEmp(Long employeeId) {
		// TODO Auto-generated method stub
		return erupiVoucherRequestRepository.findByApprovedEmployeeId(employeeId);
	}

	
	
	
}
