package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.EmployeeBulkDao;
import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.EmployeeBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.repository.bulk.EmployeeBulkUploadFailRepository;
import com.cotodel.hrms.auth.server.repository.bulk.EmployeeBulkUploadRepository;
import com.cotodel.hrms.auth.server.repository.bulk.EmployeeBulkUploadSuccessRepository;
@Service
public class EmployeeBulkDaoImpl implements EmployeeBulkDao{

	@Autowired
	EmployeeBulkUploadRepository employeeBulkUploadRepository;
	
	@Autowired
	EmployeeBulkUploadSuccessRepository employeeBulkUploadSuccessRepository;
	
	@Autowired
	EmployeeBulkUploadFailRepository employeeBulkUploadFailRepository;
	
	
	@Override
	public EmployeeBulkUploadEntity saveDetails(EmployeeBulkUploadEntity erBulkUploadEntity) {
		
		return employeeBulkUploadRepository.saveAndFlush(erBulkUploadEntity);
	}

	@Override
	public EmployeeBulkUploadSuccessEntity saveSuccessDetails(EmployeeBulkUploadSuccessEntity erBulkUploadEntity) {
		
		return employeeBulkUploadSuccessRepository.saveAndFlush(erBulkUploadEntity);
	}

	@Override
	public EmployeeBulkUploadFailEntity saveFailDetails(EmployeeBulkUploadFailEntity erBulkUploadEntity) {
		// TODO Auto-generated method stub
		return employeeBulkUploadFailRepository.saveAndFlush(erBulkUploadEntity);
	}

	@Override
	public List<EmployeeBulkUploadSuccessEntity> findSuccessList(Long orgId, String fileName) {
		// TODO Auto-generated method stub
		return employeeBulkUploadSuccessRepository.findByOrgIdAndFileName(orgId, fileName);
	}

	@Override
	public List<EmployeeBulkUploadFailEntity> findFailList(Long orgId, String fileName) {
		// TODO Auto-generated method stub
		return employeeBulkUploadFailRepository.findByOrgIdAndFileName(orgId, fileName);
	}

	@Override
	public EmployeeBulkUploadSuccessEntity findSuccessDetails(Long id) {
		// TODO Auto-generated method stub
		return employeeBulkUploadSuccessRepository.findByIdWithFlagN(id);
	}

	@Override
	public int updateSuccessFlag(Long id) {
		// TODO Auto-generated method stub
		return employeeBulkUploadSuccessRepository.updateSuccessFlag(id);
	}
//
//	@Override
//	public int updateSuccessStatus(Long id) {
//		// TODO Auto-generated method stub
//		return voucherBulkUploadSuccessRepository.updateSuccessStatus(id);
//	}
//
//	@Override
//	public VoucherMasterUploadEntity saveDetails(VoucherMasterUploadEntity voucherMasterUploadEntity) {
//		// TODO Auto-generated method stub
//		return voucherMasterUploadRepository.saveAndFlush(voucherMasterUploadEntity);
//	}
//	
	

}
