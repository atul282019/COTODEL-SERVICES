package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ProjectDao;
import com.cotodel.hrms.auth.server.model.ProjectEntity;
import com.cotodel.hrms.auth.server.repository.ProjectRepository;
@Repository
public class ProjectDaoImpl implements ProjectDao{

	@Autowired
	ProjectRepository projectRepository;

	@Override
	public ProjectEntity saveDetails(ProjectEntity projectEntity) {
		// TODO Auto-generated method stub
		return projectRepository.saveAndFlush(projectEntity);
	}

	@Override
	public List<ProjectEntity> getProject(Long emplrid) {
		// TODO Auto-generated method stub
		return projectRepository.findByProjectId(emplrid);
	}

}
