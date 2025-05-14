/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;

/**
 * 
 */
public interface EmployerDetailsDao {
	
	public EmployerDetailsEntity saveCompanyDetails(EmployerDetailsEntity company);
	public EmployerDetailsEntity getEmployerDetails(Long employerId);
	public EmployerDetailsEntity getEmployerOnboardingDetails(String companyId,String hrmsId);
	public List<EmployerDetailsEntity> checkEmployerOnboardingDetails(String organizationName,String mobile);

}
