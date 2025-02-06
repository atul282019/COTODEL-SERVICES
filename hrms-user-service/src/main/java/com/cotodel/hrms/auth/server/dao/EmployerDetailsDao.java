/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao;

import com.cotodel.hrms.auth.server.entity.EmployerDetailsEntity;

/**
 * 
 */
public interface EmployerDetailsDao {
	
	public EmployerDetailsEntity saveCompanyDetails(EmployerDetailsEntity company);
	public EmployerDetailsEntity getEmployerDetails(Long employerId);
}
