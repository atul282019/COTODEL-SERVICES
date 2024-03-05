package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.ProjectEntity;

public interface ProjectDao {
	public ProjectEntity saveDetails(ProjectEntity projectEntity);
	public List<ProjectEntity> getProject(Long emplrid);
}
