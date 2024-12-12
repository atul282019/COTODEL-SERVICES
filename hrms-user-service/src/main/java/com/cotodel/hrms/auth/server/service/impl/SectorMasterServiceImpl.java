/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.SectorMasterDao;
import com.cotodel.hrms.auth.server.entity.SectorMaster;
import com.cotodel.hrms.auth.server.service.SectorMasterService;


@Repository
public class SectorMasterServiceImpl implements SectorMasterService {

	@Autowired
	public SectorMasterDao sectorMasterDao;

	@Override
	public List<SectorMaster> getBySectorList() {
		// TODO Auto-generated method stub
		return sectorMasterDao.getBySectorList();
	}

	
}
