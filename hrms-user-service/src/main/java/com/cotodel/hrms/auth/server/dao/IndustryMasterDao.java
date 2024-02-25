package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.IndustryMaster;

public interface IndustryMasterDao {
	public List<IndustryMaster> getByIndustryList();
}
