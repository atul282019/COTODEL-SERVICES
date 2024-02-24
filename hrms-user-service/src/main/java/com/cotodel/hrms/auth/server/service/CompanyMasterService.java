/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.CompanyMaster;

/**
 * 
 */
public interface CompanyMasterService {
	
	public List<CompanyMaster> getByCompanyList();

}
