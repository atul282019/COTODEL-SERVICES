package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeDetailsDao;
import com.cotodel.hrms.auth.server.dao.EmployeeFamilyDetailsDao;
import com.cotodel.hrms.auth.server.dao.ExperienceDao;
import com.cotodel.hrms.auth.server.dao.QualificationDao;
import com.cotodel.hrms.auth.server.dto.EmployeeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeFamilyDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ExperienceRequest;
import com.cotodel.hrms.auth.server.dto.QualificationRequest;
import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;
import com.cotodel.hrms.auth.server.model.EmployeeFamilyDetailEntity;
import com.cotodel.hrms.auth.server.model.ExperienceEntity;
import com.cotodel.hrms.auth.server.model.QualificationEntity;
import com.cotodel.hrms.auth.server.service.EmployeeDetailsService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService{

	

	@Autowired
	EmployeeDetailsDao  employeeDetailsDao;
	
	@Autowired
	EmployeeFamilyDetailsDao  employeeFamilyDetailsDao;
	
	@Autowired
	QualificationDao  qualificationDao;
	
	@Autowired
	ExperienceDao  experienceDao;
	
	@Override
	public EmployeeDetailsRequest saveEmpDetails(EmployeeDetailsRequest request) {
		
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			EmployeeDetailsEntity employee=new EmployeeDetailsEntity();
			CopyUtility.copyProperties(request,employee);
			employee.setDocfile(request.getDocfile().getBytes());
			employee.setSigfile(request.getSigfile().getBytes());
			employee=employeeDetailsDao.saveDetails(employee);
			//user.setEmployeeId(employee.getEmployeeId());
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
			request.setId(employee.getEmpId());
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}

	@Override
	public List<EmployeeDetailsEntity> getEmpDetailsList(Long empid) {
		// TODO Auto-generated method stub
		return employeeDetailsDao.getEmployeeDetails(empid);
	}

	
	@Override
	public EmployeeFamilyDetailsRequest saveEmpFamilyDetails(EmployeeFamilyDetailsRequest request) {
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			EmployeeFamilyDetailEntity employee=new EmployeeFamilyDetailEntity();
			CopyUtility.copyProperties(request,employee);
			employee=employeeFamilyDetailsDao.saveDetails(employee);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}

		return request;
	}

	@Override
	public List<EmployeeFamilyDetailEntity> getEmpFamilyDetailsList(Long empid) {
		// TODO Auto-generated method stub
		return employeeFamilyDetailsDao.getEmployeeDetails(empid);
	}

	@Override
	public QualificationRequest saveQualification(QualificationRequest request) {
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			QualificationEntity employee=new QualificationEntity();
			CopyUtility.copyProperties(request,employee);
			employee=qualificationDao.saveDetails(employee);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}

		return request;

	}

	@Override
	public List<QualificationEntity> getQualificationList(Long empid) {
		
		return qualificationDao.getQualification(empid);
	}

	@Override
	public ExperienceRequest saveExperience(ExperienceRequest request) {
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			ExperienceEntity employee=new ExperienceEntity();
			CopyUtility.copyProperties(request,employee);
			employee=experienceDao.saveDetails(employee);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}

		return request;		
	}

	@Override
	public List<ExperienceEntity> getExperienceList(Long empid) {
		// TODO Auto-generated method stub
		return null;
	}
		

}
