/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.EmployerMaster;

/**
 * 
 */
public interface EmployerMasterService {
	
	public EmployerMaster getByEmployerCode(String employerCode);
	public List<EmployerMaster> getByEmployerList();

}
