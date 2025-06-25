package com.cotodel.hrms.auth.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.BandDao;
import com.cotodel.hrms.auth.server.model.BandEntity;
import com.cotodel.hrms.auth.server.repository.BandRepository;
@Repository
public class BandDaoImpl implements BandDao{

	@Autowired
	BandRepository bandRepository;

	@Override
	public BandEntity saveDetails(BandEntity bandEntity) {
		// TODO Auto-generated method stub
		return bandRepository.saveAndFlush(bandEntity);
	}

}
