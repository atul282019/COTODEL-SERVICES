/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.CompanyMasterDao;
import com.cotodel.hrms.auth.server.dto.CompanyRequest;
import com.cotodel.hrms.auth.server.entity.CompanyMaster;
import com.cotodel.hrms.auth.server.entity.EmployerEntity;
import com.cotodel.hrms.auth.server.service.CompanyMasterService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;

/**
 * 
 */

@Repository
public class CompanyMasterServiceImpl implements CompanyMasterService {

	@Autowired
	public CompanyMasterDao companyMasterDao;
	
	

	@Override
	public List<CompanyMaster> getByCompanyList() {
		// TODO Auto-generated method stub
		return companyMasterDao.getByCompanyList();
	}



	@Override
	public String saveCompanyDetails(CompanyRequest company) {
		
		CompanyMaster companyMaster= new CompanyMaster();
		EmployerEntity employerEntity=null;
		String response="";
		try {			
			CopyUtility.copyProperties(company,companyMaster);
			companyMasterDao.saveCompanyDetails(companyMaster);		
			response=MessageConstant.RESPONSE_SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			response=MessageConstant.RESPONSE_FAILED;
		}
		return response;
	}
	

}
