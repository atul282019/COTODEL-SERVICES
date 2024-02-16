/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.EmployerMasterDao;
import com.cotodel.hrms.auth.server.entity.EmployerMaster;
import com.cotodel.hrms.auth.server.service.EmployerMasterService;

/**
 * 
 */

@Service
public class EmployerMasterServiceImpl implements EmployerMasterService {

	@Autowired
	public EmployerMasterDao employerMasterDao;
	
	@Override
	public EmployerMaster getByEmployerCode(String employerCode) {
		// TODO Auto-generated method stub
		return employerMasterDao.getByEmployerCode(employerCode);
	}

	@Override
	public List<EmployerMaster> getByEmployerList() {
		// TODO Auto-generated method stub
		return employerMasterDao.getByEmployerList();
	}

}
