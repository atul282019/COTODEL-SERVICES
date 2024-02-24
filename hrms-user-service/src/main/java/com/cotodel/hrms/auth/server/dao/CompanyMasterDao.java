/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.CompanyMaster;

/**
 * 
 */
public interface CompanyMasterDao {
	
	public List<CompanyMaster> getByCompanyList();
}
