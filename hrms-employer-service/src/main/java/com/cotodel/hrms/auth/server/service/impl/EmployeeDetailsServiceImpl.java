package com.cotodel.hrms.auth.server.service.impl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeDetailsDao;
import com.cotodel.hrms.auth.server.dto.EmployeeDetailsRequest;
import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;
import com.cotodel.hrms.auth.server.service.EmployeeDetailsService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService{

	

	@Autowired
	EmployeeDetailsDao  employeeDetailsDao;
	
			
	@Override
	public EmployeeDetailsRequest saveEmpDetails(EmployeeDetailsRequest request) {
		
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			EmployeeDetailsEntity employee=new EmployeeDetailsEntity();
			CopyUtility.copyProperties(request,employee);
			if(request.getDocfile()!=null)
				employee.setDocfile(Base64.getDecoder().decode(request.getDocfile()));
			if(request.getSigfile()!=null)
				employee.setSigfile(Base64.getDecoder().decode(request.getSigfile()));
			employee=employeeDetailsDao.saveDetails(employee);
			
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
			request.setId(employee.getEmpId());
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}

//	@Override
//	public List<EmployeeDetailsEntity> getEmpDetailsList(Long empid) {
//		// TODO Auto-generated method stub
//		return employeeDetailsDao.getEmployeeDetails(empid);
//	}

	
//	@Override
//	public EmployeeFamilyDetailsRequest saveEmpFamilyDetails(EmployeeFamilyDetailsRequest request) {
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);		
//			EmployeeFamilyDetailEntity employee=new EmployeeFamilyDetailEntity();
//			CopyUtility.copyProperties(request,employee);
//			employee=employeeFamilyDetailsDao.saveDetails(employee);
//			response=MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//		}
//
//		return request;
//	}

//	@Override
//	public List<EmployeeFamilyDetailEntity> getEmpFamilyDetailsList(Long empid) {
//		// TODO Auto-generated method stub
//		return employeeFamilyDetailsDao.getEmployeeDetails(empid);
//	}

//	@Override
//	public QualificationRequest saveQualification(QualificationRequest request) {
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);		
//			QualificationEntity employee=new QualificationEntity();
//			CopyUtility.copyProperties(request,employee);
//			if(request.getDocfile()!=null)
//				employee.setDocfile(Base64.getDecoder().decode(request.getDocfile()));
//			employee=qualificationDao.saveDetails(employee);
//			response=MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//		}
//
//		return request;
//
//	}
//
//	@Override
//	public List<QualificationEntity> getQualificationList(Long empid,Long employerid) {
//		
//		return qualificationDao.getQualification(empid,employerid);
//	}

//	@Override
//	public ExperienceRequest saveExperience(ExperienceRequest request) {
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);		
//			ExperienceEntity employee=new ExperienceEntity();
//			CopyUtility.copyProperties(request,employee);
//			if(request.getDocfile()!=null)
//				employee.setDocfile(Base64.getDecoder().decode(request.getDocfile()));
//			employee=experienceDao.saveDetails(employee);
//			response=MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//		}
//
//		return request;		
//	}

//	@Override
//	public List<ExperienceEntity> getExperienceList(Long empid) {
//		// TODO Auto-generated method stub
//		return experienceDao.getExperience(empid);
//	}

//	@Override
//	public CertificateRequest saveCertificate(CertificateRequest request) {
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);		
//			CertificateEntity certificateEntity=new CertificateEntity();
//			CopyUtility.copyProperties(request,certificateEntity);
//			if(request.getDocfile()!=null)				
//				certificateEntity.setDocfile(Base64.getDecoder().decode(request.getDocfile()));
//			certificateEntity=certificateDao.saveDetails(certificateEntity);
//			response=MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//		}
//
//		return request;
//	}

//	@Override
//	public List<CertificateEntity> getCertificateList(Long empid) {		
//		return certificateDao.getCertificate(empid);
//	}

//	@Override
//	public ProjectRequest saveProject(ProjectRequest request) {
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);		
//			ProjectEntity projectEntity=new ProjectEntity();
//			CopyUtility.copyProperties(request,projectEntity);
//			projectEntity=projectDao.saveDetails(projectEntity);
//			response=MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//		}
//
//		return request;
//	}
//
//	@Override
//	public List<ProjectEntity> getProjectList(Long empid) {
//		// TODO Auto-generated method stub
//		return projectDao.getProject(empid);
//	}
		

	
}
