package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ExperienceEntity;

public interface ExperienceDao {
	public ExperienceEntity saveDetails(ExperienceEntity experienceEntity);
	public List<ExperienceEntity> getExperience(Long emplrid);
}
