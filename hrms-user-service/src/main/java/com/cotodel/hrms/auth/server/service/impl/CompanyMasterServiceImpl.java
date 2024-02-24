/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.CompanyMasterDao;
import com.cotodel.hrms.auth.server.dao.StateMasterDao;
import com.cotodel.hrms.auth.server.entity.CompanyMaster;
import com.cotodel.hrms.auth.server.entity.StateMaster;
import com.cotodel.hrms.auth.server.service.CompanyMasterService;
import com.cotodel.hrms.auth.server.service.StateMasterService;

/**
 * 
 */

@Service
public class CompanyMasterServiceImpl implements CompanyMasterService {

	@Autowired
	public CompanyMasterDao companyMasterDao;
	
	

	@Override
	public List<CompanyMaster> getByCompanyList() {
		// TODO Auto-generated method stub
		return companyMasterDao.getByCompanyList();
	}

}
