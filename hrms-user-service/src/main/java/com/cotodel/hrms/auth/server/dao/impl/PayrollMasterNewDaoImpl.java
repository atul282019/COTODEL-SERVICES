/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.PayrollMasterNewDao;
import com.cotodel.hrms.auth.server.entity.PayrollMasterNewEntity;
import com.cotodel.hrms.auth.server.repository.PayrollMasterNewRepository;

/**
 * 
 */
@Repository
public class PayrollMasterNewDaoImpl implements PayrollMasterNewDao {

	
	@Autowired
	public  PayrollMasterNewRepository payrollMasterNewRepository;

	@Override
	public List<PayrollMasterNewEntity> getByPayrollMasterNewList() {
		// TODO Auto-generated method stub
		return payrollMasterNewRepository.getByPayrollMasterNewList();
	}
	
	
}
