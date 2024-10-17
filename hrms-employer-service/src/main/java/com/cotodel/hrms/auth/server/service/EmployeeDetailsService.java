package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.EmployeeDetailsRequest;
import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;

public interface EmployeeDetailsService {
	
	public EmployeeDetailsRequest  saveEmpDetails(EmployeeDetailsRequest request);	
	//public List<EmployeeDetailsEntity>  getEmpDetailsList(Long empid);	
	//public EmployeeFamilyDetailsRequest  saveEmpFamilyDetails(EmployeeFamilyDetailsRequest request);
	//public List<EmployeeFamilyDetailEntity>  getEmpFamilyDetailsList(Long empid);
	//public QualificationRequest  saveQualification(QualificationRequest request);
	//public List<QualificationEntity>  getQualificationList(Long empid,Long employerid);
	//public ExperienceRequest  saveExperience(ExperienceRequest request);
	//public List<ExperienceEntity>  getExperienceList(Long empid);
	//public CertificateRequest  saveCertificate(CertificateRequest request);
	//public List<CertificateEntity>  getCertificateList(Long empid);
	//public ProjectRequest  saveProject(ProjectRequest request);
	//public List<ProjectEntity>  getProjectList(Long empid);
	
}
