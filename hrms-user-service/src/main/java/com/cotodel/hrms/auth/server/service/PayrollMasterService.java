/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.PayrollMasterEntity;

/**
 * 
 */
public interface PayrollMasterService {
	
	public List<PayrollMasterEntity> getByPayrollMasterList();

}
