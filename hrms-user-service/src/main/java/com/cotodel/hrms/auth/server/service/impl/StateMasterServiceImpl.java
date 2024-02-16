/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.dao.StateMasterDao;
import com.cotodel.hrms.auth.server.entity.StateMaster;
import com.cotodel.hrms.auth.server.service.StateMasterService;

/**
 * 
 */

@Service
public class StateMasterServiceImpl implements StateMasterService {

	@Autowired
	public StateMasterDao stateMasterDao;
	
	@Override
	public StateMaster getByStateCode(String stateCode) {
		// TODO Auto-generated method stub
		return stateMasterDao.getByStateCode(stateCode);
	}

	@Override
	public List<StateMaster> getByStateList() {
		// TODO Auto-generated method stub
		return stateMasterDao.getByStateList();
	}

}
