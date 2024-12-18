package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.ErupiVoucherBulkDao;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.model.bulk.VoucherMasterUploadEntity;
import com.cotodel.hrms.auth.server.repository.bulk.VoucherBulkUploadFailRepository;
import com.cotodel.hrms.auth.server.repository.bulk.VoucherBulkUploadRepository;
import com.cotodel.hrms.auth.server.repository.bulk.VoucherBulkUploadSuccessRepository;
import com.cotodel.hrms.auth.server.repository.bulk.VoucherMasterUploadRepository;
@Service
public class ErupiVoucherBulkDaoImpl implements ErupiVoucherBulkDao{

	@Autowired
	VoucherBulkUploadRepository voucherBulkUploadRepository;
	
	@Autowired
	VoucherBulkUploadSuccessRepository voucherBulkUploadSuccessRepository;
	
	@Autowired
	VoucherBulkUploadFailRepository voucherBulkUploadFailRepository;
	
	@Autowired
	VoucherMasterUploadRepository voucherMasterUploadRepository;
	
	@Override
	public VoucherBulkUploadEntity saveDetails(VoucherBulkUploadEntity erBulkUploadEntity) {
		
		return voucherBulkUploadRepository.saveAndFlush(erBulkUploadEntity);
	}

	@Override
	public VoucherBulkUploadSuccessEntity saveSuccessDetails(VoucherBulkUploadSuccessEntity erBulkUploadEntity) {
		
		return voucherBulkUploadSuccessRepository.saveAndFlush(erBulkUploadEntity);
	}

	@Override
	public VoucherBulkUploadFailEntity saveFailDetails(VoucherBulkUploadFailEntity erBulkUploadEntity) {
		
		return voucherBulkUploadFailRepository.saveAndFlush(erBulkUploadEntity);
	}

	@Override
	public List<VoucherBulkUploadSuccessEntity> findSuccessList(Long orgId, String fileName) {
		// TODO Auto-generated method stub
		return voucherBulkUploadSuccessRepository.findByOrgIdAndFileName(orgId, fileName);
	}

	@Override
	public List<VoucherBulkUploadFailEntity> findFailList(Long orgId, String fileName) {
		// TODO Auto-generated method stub
		return voucherBulkUploadFailRepository.findByOrgIdAndFileName(orgId, fileName);
	}

	@Override
	public VoucherBulkUploadSuccessEntity findSuccessDetails(Long id) {
		// TODO Auto-generated method stub
		return voucherBulkUploadSuccessRepository.findByIdWithFlagN(id);
	}

	@Override
	public int updateSuccessFlag(Long id) {
		// TODO Auto-generated method stub
		return voucherBulkUploadSuccessRepository.updateSuccessFlag(id);
	}

	@Override
	public int updateSuccessStatus(Long id) {
		// TODO Auto-generated method stub
		return voucherBulkUploadSuccessRepository.updateSuccessStatus(id);
	}

	@Override
	public VoucherMasterUploadEntity saveDetails(VoucherMasterUploadEntity voucherMasterUploadEntity) {
		// TODO Auto-generated method stub
		return voucherMasterUploadRepository.saveAndFlush(voucherMasterUploadEntity);
	}
	
	

}
