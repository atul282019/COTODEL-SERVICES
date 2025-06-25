package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.DirectorOnboardingRequest;
import com.cotodel.hrms.auth.server.model.DirectorOnboardingEntity;

public interface DirectorOnboardingService {
	
	public DirectorOnboardingRequest  saveDirectorDetails(DirectorOnboardingRequest	 request);
	public List<DirectorOnboardingEntity>  getDirectorDetailsList(Long employerid);
}
