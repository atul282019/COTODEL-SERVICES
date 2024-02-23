/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.PayrollMasterDao;
import com.cotodel.hrms.auth.server.dao.StateMasterDao;
import com.cotodel.hrms.auth.server.entity.PayrollMasterEntity;
import com.cotodel.hrms.auth.server.entity.StateMaster;
import com.cotodel.hrms.auth.server.service.PayrollMasterService;
import com.cotodel.hrms.auth.server.service.StateMasterService;

/**
 * 
 */

@Service
public class PayrollMasterServiceImpl implements PayrollMasterService {

	@Autowired
	public PayrollMasterDao payrollMasterDao;
	
	

	@Override
	public List<PayrollMasterEntity> getByPayrollMasterList() {
		// TODO Auto-generated method stub
		return payrollMasterDao.getByPayrollMasterList();
	}

}
