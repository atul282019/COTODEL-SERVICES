/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.PayrollMasterNewEntity;

/**
 * 
 */
public interface PayrollMasterNewDao {
	
	public List<PayrollMasterNewEntity> getByPayrollMasterNewList();
}
