/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.EmployerMaster;
import com.cotodel.hrms.auth.server.entity.StateMaster;

/**
 * 
 */
public interface StateMasterService {
	
	public StateMaster getByStateCode(String stateCode);
	public List<StateMaster> getByStateList();

}
