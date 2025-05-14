
package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployerDetailsDao;
import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;
import com.cotodel.hrms.auth.server.repository.EmployerDetailsRepository;


@Repository
public class EmployerDetailsDaoImpl implements EmployerDetailsDao {

	
	@Autowired
	public  EmployerDetailsRepository employerDetailsRepository;

	@Override
	public EmployerDetailsEntity saveCompanyDetails(EmployerDetailsEntity employerDetails) {
		return employerDetailsRepository.saveAndFlush(employerDetails);
	}

	@Override
	public EmployerDetailsEntity getEmployerDetails(Long employerId) {
		// TODO Auto-generated method stub
		return employerDetailsRepository.getEmployerDetailsByEmpId(employerId);
	}

	@Override
	public EmployerDetailsEntity getEmployerOnboardingDetails(String companyId, String hrmsId) {
		
		return employerDetailsRepository.getEmployerDetailsByCompId(companyId, hrmsId);
	}

	@Override
	public List<EmployerDetailsEntity> checkEmployerOnboardingDetails(String organizationName, String mobile) {
		// TODO Auto-generated method stub
		return employerDetailsRepository.checkOrgAndMobile(organizationName, mobile);
	}

}
