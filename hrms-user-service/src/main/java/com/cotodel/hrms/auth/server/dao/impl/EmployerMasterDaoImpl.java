/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployerMasterDao;
import com.cotodel.hrms.auth.server.entity.EmployerMaster;
import com.cotodel.hrms.auth.server.repository.EmployerMasterRepository;

/**
 * 
 */
@Repository
public class EmployerMasterDaoImpl implements EmployerMasterDao {

	
	@Autowired
	public  EmployerMasterRepository employerMasterRepository;
	
	@Override
	public EmployerMaster getByEmployerCode(String employerCode) {
		// TODO Auto-generated method stub
		return employerMasterRepository.getByEmployerCode(employerCode);
	}

	@Override
	public List<EmployerMaster> getByEmployerList() {
		// TODO Auto-generated method stub
		return employerMasterRepository.getByEmployerList();
	}

}
