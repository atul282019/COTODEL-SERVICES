/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.PayrollMasterEntity;

/**
 * 
 */
public interface PayrollMasterDao {
	
	public List<PayrollMasterEntity> getByPayrollMasterList();
}
