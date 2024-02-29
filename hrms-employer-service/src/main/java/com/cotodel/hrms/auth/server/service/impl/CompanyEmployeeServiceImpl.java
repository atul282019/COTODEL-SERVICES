package com.cotodel.hrms.auth.server.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CompanyEmployeeDao;
import com.cotodel.hrms.auth.server.dao.LeaveRequestDao;
import com.cotodel.hrms.auth.server.dao.PerformanceReviewDao;
import com.cotodel.hrms.auth.server.dao.TimesheetDao;
import com.cotodel.hrms.auth.server.dto.CompanyEmployeeRequest;
import com.cotodel.hrms.auth.server.model.CompanyEmployeeEntity;
import com.cotodel.hrms.auth.server.model.EmployerEntity;
import com.cotodel.hrms.auth.server.model.LeaveRequest;
import com.cotodel.hrms.auth.server.model.PerformanceReview;
import com.cotodel.hrms.auth.server.model.Timesheet;
import com.cotodel.hrms.auth.server.service.CompanyEmployeeService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class CompanyEmployeeServiceImpl implements CompanyEmployeeService{

	@Autowired
	CompanyEmployeeDao  companyEmployeeDao;	
	
	@Autowired
	LeaveRequestDao  leaveRequestDao;
	
	@Autowired
	TimesheetDao  timesheetDao;
	
	@Autowired
	PerformanceReviewDao  performanceReviewDao;

	@Override
	@Transactional
	public CompanyEmployeeRequest saveCompEmployeeDetails(CompanyEmployeeRequest request) {
		EmployerEntity employerEntity=null;
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			CompanyEmployeeEntity employee=new CompanyEmployeeEntity();
			LeaveRequest leaveRequestEntity=new LeaveRequest();
			Timesheet timesheet=new Timesheet();
			PerformanceReview perEntity=new PerformanceReview();
			CopyUtility.copyProperties(request.getEmployee(),employee);
		
			employee=companyEmployeeDao.saveDetails(employee);
			
			CopyUtility.copyProperties(request.getLeaveRequest(),leaveRequestEntity);
			if(leaveRequestEntity!=null) {
			leaveRequestEntity.setEmployeeId(employee.getId());
			leaveRequestEntity=leaveRequestDao.saveDetails(leaveRequestEntity);
			}
			//timesheet
			CopyUtility.copyProperties(request.getTimesheet(),timesheet);
			if(timesheet!=null) {
				timesheet.setEmployeeId(employee.getId());
				timesheet=timesheetDao.saveDetails(timesheet);
			}
			
			//performanceReview
			CopyUtility.copyProperties(request.getPerformanceReview(),perEntity);
			if(perEntity!=null) {
				perEntity.setEmployeeId(employee.getId());
				perEntity=performanceReviewDao.saveDetails(perEntity);
			}
			
			
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			request.setResponse(response);
		}
		return request;

	}


	@Override
	public CompanyEmployeeRequest getCompEmployeeDetails(Long employeeid) {
		CompanyEmployeeRequest companyEmployeeRequest=new CompanyEmployeeRequest();
		String response=MessageConstant.RESPONSE_FAILED;
		try {
			CompanyEmployeeEntity employee=companyEmployeeDao.getCompanyEmployee(employeeid);
			LeaveRequest leaveRequest=leaveRequestDao.getLeaveRequest(employeeid);
			Timesheet timesheet=timesheetDao.getTimesheet(employeeid);
			PerformanceReview performanceReview=performanceReviewDao.getPerformanceReview(employeeid);
//			employee.setLeaveRequest(leaveRequest);
//			employee.setTimesheet(timesheet);
//			employee.setPerformanceReview(performanceReview);
			
			companyEmployeeRequest.setEmployee(employee);
			companyEmployeeRequest.setLeaveRequest(leaveRequest);
			companyEmployeeRequest.setTimesheet(timesheet);
			companyEmployeeRequest.setPerformanceReview(performanceReview);
			CopyUtility.copyProperties(companyEmployeeRequest, companyEmployeeRequest);
			response=MessageConstant.RESPONSE_SUCCESS;
			companyEmployeeRequest.setResponse(response);
			//System.out.println(employee.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response=MessageConstant.RESPONSE_FAILED;
			companyEmployeeRequest.setResponse(response);
		}
		return companyEmployeeRequest;
	}


	
}
