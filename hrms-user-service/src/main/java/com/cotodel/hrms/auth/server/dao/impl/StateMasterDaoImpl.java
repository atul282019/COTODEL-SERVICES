/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.StateMasterDao;
import com.cotodel.hrms.auth.server.entity.StateMaster;
import com.cotodel.hrms.auth.server.repository.StateMasterRepository;

/**
 * 
 */
@Repository
public class StateMasterDaoImpl implements StateMasterDao {

	
	@Autowired
	public  StateMasterRepository stateMasterRepository;
	
	
	
	@Override
	public StateMaster getByStateCode(String statecode) {
		// TODO Auto-generated method stub
		return stateMasterRepository.getByStateCode(statecode);
	}

	@Override
	public List<StateMaster> getByStateList() {
		// TODO Auto-generated method stub
		return stateMasterRepository.getByStateList();
	}

	

	
}
