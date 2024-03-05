package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExperienceDao;
import com.cotodel.hrms.auth.server.model.ExperienceEntity;
import com.cotodel.hrms.auth.server.repository.ExperienceRepository;
@Repository
public class ExperienceDaoImpl implements ExperienceDao{

	@Autowired
	ExperienceRepository experienceRepository;

	@Override
	public ExperienceEntity saveDetails(ExperienceEntity experienceEntity) {
		// TODO Auto-generated method stub
		return experienceRepository.saveAndFlush(experienceEntity);
	}

	@Override
	public List<ExperienceEntity> getExperience(Long emplrid) {
		// TODO Auto-generated method stub
		return experienceRepository.findByExperienceId(emplrid);
	}
	
	
	
	

}
