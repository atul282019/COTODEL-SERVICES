package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.SectorMasterDao;
import com.cotodel.hrms.auth.server.entity.SectorMaster;
import com.cotodel.hrms.auth.server.repository.SectorMasterRepository;

@Repository
public class SectorMasterDaoImpl implements SectorMasterDao{

	@Autowired
	SectorMasterRepository sectorMasterRepository;

	@Override
	public List<SectorMaster> getBySectorList() {
		// TODO Auto-generated method stub
		return sectorMasterRepository.getBySectorMasterList();
	}
		
}
