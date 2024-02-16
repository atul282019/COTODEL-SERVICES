/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.EmployerMaster;
import com.cotodel.hrms.auth.server.entity.StateMaster;

/**
 * 
 */
public interface StateMasterDao {
	
	public StateMaster getByStateCode(String statecode);
	public List<StateMaster> getByStateList();
}
