/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.PayrollMasterNewDao;
import com.cotodel.hrms.auth.server.entity.PayrollMasterNewEntity;
import com.cotodel.hrms.auth.server.service.PayrollMasterNewService;

/**
 * 
 */

@Service
public class PayrollMasterNewServiceImpl implements PayrollMasterNewService {

	@Autowired
	public PayrollMasterNewDao payrollMasterNewDao;

	@Override
	public List<PayrollMasterNewEntity> getByPayrollMasterNewList() {
		// TODO Auto-generated method stub
		return payrollMasterNewDao.getByPayrollMasterNewList();
	}
	
	

	

}
