/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.IndustryMasterDao;
import com.cotodel.hrms.auth.server.entity.IndustryMaster;
import com.cotodel.hrms.auth.server.service.IndustryMasterService;


@Repository
public class IndustryMasterServiceImpl implements IndustryMasterService {

	@Autowired
	public IndustryMasterDao industryMasterDao;

	@Override
	public List<IndustryMaster> getByIndustryList() {
		
		return industryMasterDao.getByIndustryList();
	}
	
}
