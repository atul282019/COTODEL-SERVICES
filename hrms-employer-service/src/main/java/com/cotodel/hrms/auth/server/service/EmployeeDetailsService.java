package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.CertificateRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeDetailsRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeFamilyDetailsRequest;
import com.cotodel.hrms.auth.server.dto.ExperienceRequest;
import com.cotodel.hrms.auth.server.dto.QualificationRequest;
import com.cotodel.hrms.auth.server.model.CertificateEntity;
import com.cotodel.hrms.auth.server.model.EmployeeDetailsEntity;
import com.cotodel.hrms.auth.server.model.EmployeeFamilyDetailEntity;
import com.cotodel.hrms.auth.server.model.ExperienceEntity;
import com.cotodel.hrms.auth.server.model.QualificationEntity;

public interface EmployeeDetailsService {
	
	public EmployeeDetailsRequest  saveEmpDetails(EmployeeDetailsRequest request);	
	public List<EmployeeDetailsEntity>  getEmpDetailsList(Long empid);	
	public EmployeeFamilyDetailsRequest  saveEmpFamilyDetails(EmployeeFamilyDetailsRequest request);
	public List<EmployeeFamilyDetailEntity>  getEmpFamilyDetailsList(Long empid);
	public QualificationRequest  saveQualification(QualificationRequest request);
	public List<QualificationEntity>  getQualificationList(Long empid);
	public ExperienceRequest  saveExperience(ExperienceRequest request);
	public List<ExperienceEntity>  getExperienceList(Long empid);
	public CertificateRequest  saveCertificate(CertificateRequest request);
	public List<CertificateEntity>  getCertificateList(Long empid);
}
