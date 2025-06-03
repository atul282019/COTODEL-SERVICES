package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.VehicleBulkUploadDao;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleBulkUploadSuccessEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehicleManagementEntity;
import com.cotodel.hrms.auth.server.model.vehicle.VehiclerBulkUploadFailEntity;
import com.cotodel.hrms.auth.server.repository.vehicle.VehicleBulkUploadRepository;
import com.cotodel.hrms.auth.server.repository.vehicle.VehicleBulkUploadSuccessRepository;
import com.cotodel.hrms.auth.server.repository.vehicle.VehicleManagementRepository;
import com.cotodel.hrms.auth.server.repository.vehicle.VehiclerBulkUploadFailRepository;
@Service
public class VehicleBulkUploadDaoImpl implements VehicleBulkUploadDao{

	@Autowired
	VehicleBulkUploadRepository vehicleBulkUploadRepository;
	
	@Autowired
	VehicleBulkUploadSuccessRepository vehicleBulkUploadSuccessRepository;
	
	@Autowired
	VehiclerBulkUploadFailRepository vehiclerBulkUploadFailRepository;
	
	@Autowired
	VehicleManagementRepository vehicleManagementRepository;
	
	@Override
	public VehicleBulkUploadEntity saveDetails(VehicleBulkUploadEntity vehicleBulkUploadEntity) {
		
		return vehicleBulkUploadRepository.saveAndFlush(vehicleBulkUploadEntity);
	}


	@Override
	public VehicleBulkUploadSuccessEntity saveSuccessDetails(
			VehicleBulkUploadSuccessEntity vehicleBulkUploadSuccessEntity) {
		// TODO Auto-generated method stub
		return vehicleBulkUploadSuccessRepository.saveAndFlush(vehicleBulkUploadSuccessEntity);
	}


	@Override
	public VehiclerBulkUploadFailEntity saveFailDetails(VehiclerBulkUploadFailEntity vehiclerBulkUploadFailEntity) {
		// TODO Auto-generated method stub
		return vehiclerBulkUploadFailRepository.saveAndFlush(vehiclerBulkUploadFailEntity);
	}


	@Override
	public List<VehicleBulkUploadSuccessEntity> findSuccessList(Long orgId, String fileName) {
		// TODO Auto-generated method stub
		return vehicleBulkUploadSuccessRepository.findByOrgIdAndFileName(orgId, fileName);
	}


	@Override
	public List<VehiclerBulkUploadFailEntity> findFailList(Long orgId, String fileName) {
		// TODO Auto-generated method stub
		return vehiclerBulkUploadFailRepository.findByOrgIdAndFileName(orgId, fileName);
	}


	@Override
	public VehicleBulkUploadSuccessEntity findSuccessDetails(Long id) {
		// TODO Auto-generated method stub
		return vehicleBulkUploadSuccessRepository.findByIdWithFlagN(id);
	}

	@Override
	public int updateSuccessFlag(Long id) {
		// TODO Auto-generated method stub
		return vehicleBulkUploadSuccessRepository.updateSuccessFlag(id);
	}


	@Override
	public VehicleManagementEntity saveVehicleManagementDetails(VehicleManagementEntity vehicleManagementEntity) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
//
//	@Override
//	public List<VoucherBulkUploadSuccessEntity> findSuccessList(Long orgId, String fileName) {
//		// TODO Auto-generated method stub
//		return voucherBulkUploadSuccessRepository.findByOrgIdAndFileName(orgId, fileName);
//	}
//
//	@Override
//	public List<VoucherBulkUploadFailEntity> findFailList(Long orgId, String fileName) {
//		// TODO Auto-generated method stub
//		return voucherBulkUploadFailRepository.findByOrgIdAndFileName(orgId, fileName);
//	}
//
//	@Override
//	public VoucherBulkUploadSuccessEntity findSuccessDetails(Long id) {
//		// TODO Auto-generated method stub
//		return voucherBulkUploadSuccessRepository.findByIdWithFlagN(id);
//	}
//

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
//	

}
