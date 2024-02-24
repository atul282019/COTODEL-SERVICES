/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CompanyMasterDao;
import com.cotodel.hrms.auth.server.entity.CompanyMaster;
import com.cotodel.hrms.auth.server.repository.CompanyMasterRepository;

/**
 * 
 */
@Repository
public class CompanyMasterDaoImpl implements CompanyMasterDao {

	
	@Autowired
	public  CompanyMasterRepository companyMasterRepository;
	
	@Override
	public List<CompanyMaster> getByCompanyList() {
		return companyMasterRepository.getByCompanyList();
	}

	@Override
	public CompanyMaster saveCompanyDetails(CompanyMaster company) {
		// TODO Auto-generated method stub
		return companyMasterRepository.saveAndFlush(company);
	}


	
}
