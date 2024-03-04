package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.QualificationEntity;

public interface QualificationDao {
	public QualificationEntity saveDetails(QualificationEntity qualificationEntity);
	public List<QualificationEntity> getQualification(Long emplrid);
}
