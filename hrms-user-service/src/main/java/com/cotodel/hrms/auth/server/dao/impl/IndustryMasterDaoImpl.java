package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.IndustryMasterDao;
import com.cotodel.hrms.auth.server.entity.IndustryMaster;
import com.cotodel.hrms.auth.server.repository.IndustryMasterRepository;

@Repository
public class IndustryMasterDaoImpl implements IndustryMasterDao{

	@Autowired
	IndustryMasterRepository industryMasterRepository;
	
	
	@Override
	public List<IndustryMaster> getByIndustryList() {
		
		return industryMasterRepository.getByIndustryList();
	}
	
	
}
