package com.cotodel.hrms.auth.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.BandDao;
import com.cotodel.hrms.auth.server.dao.EmployeeBandDao;
import com.cotodel.hrms.auth.server.dto.EmployeeBandRequest;
import com.cotodel.hrms.auth.server.model.EmployeeBandEntity;
import com.cotodel.hrms.auth.server.service.EmployeeBandService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeBandServiceImpl implements EmployeeBandService{

	@Autowired
	EmployeeBandDao  employeeBandDao;
	
	@Autowired
	BandDao  bandDao;

	@Override
	@Transactional
	public EmployeeBandRequest saveCompEmployeeBandDetails(EmployeeBandRequest request) {
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);			
			
			
			EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
			CopyUtility.copyProperties(request,employeeBandEntity);
			
				employeeBandEntity=employeeBandDao.saveDetails(employeeBandEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			
			
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			request.setResponse(response);
		}
		return request;

	}


//	@Override
//	public EmployeeBandEntity getCompEmployeeBandDetails(String bandid) {
//		EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
//		
//		String response=MessageConstant.RESPONSE_FAILED;
//		
//		try {
//			employeeBandEntity=employeeBandDao.getEmployeeBandDetails(bandid);
//			
//			response=MessageConstant.RESPONSE_SUCCESS;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			response=MessageConstant.RESPONSE_FAILED;
//			//companyEmployeeRequest.setResponse(response);
//		}
//		return employeeBandEntity;
//	}


	@Override
	public List<EmployeeBandEntity> getEmployeeBandList() {
		List<EmployeeBandEntity> employeeBandEntity=new ArrayList<EmployeeBandEntity>();
		
		String response=MessageConstant.RESPONSE_FAILED;
		
		try {
			employeeBandEntity=employeeBandDao.getEmployeeBandList();
			
			response=MessageConstant.RESPONSE_SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			response=MessageConstant.RESPONSE_FAILED;
			//companyEmployeeRequest.setResponse(response);
		}
		return employeeBandEntity;
	}
	
}
