/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.EmployerMaster;

/**
 * 
 */
public interface EmployerMasterDao {
	
	public EmployerMaster getByEmployerCode(String employerCode);
	public List<EmployerMaster> getByEmployerList();
}
