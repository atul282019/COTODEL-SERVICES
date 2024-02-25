/**
 * @author vinay
 *
 * 
 */
package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.entity.IndustryMaster;

/**
 * 
 */
public interface IndustryMasterService {
	
	public List<IndustryMaster> getByIndustryList();
	
}
