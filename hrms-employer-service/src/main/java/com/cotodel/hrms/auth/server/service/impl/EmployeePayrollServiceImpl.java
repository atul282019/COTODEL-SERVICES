package com.cotodel.hrms.auth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeePayrollDao;
import com.cotodel.hrms.auth.server.dao.EmployeePayrollNewDao;
import com.cotodel.hrms.auth.server.dao.EmployeePayrollTaxDao;
import com.cotodel.hrms.auth.server.dao.EmployeeProfileDao;
import com.cotodel.hrms.auth.server.service.EmployeePayrollService;
@Repository
public class EmployeePayrollServiceImpl implements EmployeePayrollService{

	@Autowired
	EmployeePayrollDao  employeePayrollDao;	
	
	@Autowired
	EmployeePayrollNewDao  employeePayrollNewDao;	
	
	@Autowired
	EmployeeProfileDao  employeeProfileDao;	
	
	@Autowired
	EmployeePayrollTaxDao  employeePayrollTaxDao;	
	
//	@Override
//	public EmployeePayrollRequest saveEmployeePayrollDetails(EmployeePayrollRequest request) {
//		
//		EmployerEntity employerEntity=null;
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);		
//			EmployeePayrollEntity employee=new EmployeePayrollEntity();
//			CopyUtility.copyProperties(request,employee);
//		
//			employee=employeePayrollDao.saveDetails(employee);
//			
//			//
//			EmployeeProfileEntity employeeProfileEntity=new EmployeeProfileEntity();
//			employeeProfileEntity=employeeProfileDao.getEmplDetails(request.getEmployerId());
//			employeeProfileEntity.setProfileComplete(3);
//			employeeProfileDao.saveDetails(employeeProfileEntity);
//			//
//			response=MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//		}
//		return request;
//
//	}

//	@Override
//	public EmployeePayrollTaxRequest saveEmployeePayrollTaxDetails(EmployeePayrollTaxRequest request) {
//		
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);		
//			EmployeePayrollTaxEntity employee=new EmployeePayrollTaxEntity();
//			CopyUtility.copyProperties(request,employee);
//		
//			employee=employeePayrollTaxDao.saveDetails(employee);
//			
//			response=MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//		}
//		return request;
//
//	}

//	@Override
//	public EmployeePayrollRequest saveEmployeePayrollDetailsNew(EmployeePayrollRequest request) {
//		EmployerEntity employerEntity=null;
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);		
//			EmployeePayrollNewEntity employee=new EmployeePayrollNewEntity();
//			//CopyUtility.copyProperties(request,employee);
//			if(request.getList()!=null) {
//				for (EmployeePayrollNewEntity employeePayrollNewEntity : request.getList()) {
//					employee = employeePayrollNewDao.saveDetails(employeePayrollNewEntity);
//				}
//				
//				EmployeeProfileEntity employeeProfileEntity = new EmployeeProfileEntity();
//				employeeProfileEntity = employeeProfileDao.getEmplDetails(employee.getEmployerId());
//				employeeProfileEntity.setProfileComplete(6);
//				employeeProfileDao.saveDetails(employeeProfileEntity);
//				
//				response = MessageConstant.RESPONSE_SUCCESS;
//				request.setResponse(response);
//			}
//		} catch (Exception e) {
//			//e.printStackTrace();
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//		}
//		return request;
//
//	}

//	@Override
//	public EmployeeProfileEntity getEmployerPayRollDetail(Long employerId) {
//		// TODO Auto-generated method stub
//		EmployeeProfileEntity employeeProfileEntity = new EmployeeProfileEntity();
//		try {
//			employeeProfileEntity = employeeProfileDao.getEmplDetails(employerId);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		return employeeProfileEntity;
//	}
	
	
	
}
