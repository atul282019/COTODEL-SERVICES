/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.PayrollMasterDao;
import com.cotodel.hrms.auth.server.entity.PayrollMasterEntity;
import com.cotodel.hrms.auth.server.repository.PayrollMasterRepository;

/**
 * 
 */
@Repository
public class PayrollMasterDaoImpl implements PayrollMasterDao {

	
	@Autowired
	public  PayrollMasterRepository payrollMasterRepository;
	
	@Override
	public List<PayrollMasterEntity> getByPayrollMasterList() {
		
		return payrollMasterRepository.getByPayrollMasterList();
	}
}
