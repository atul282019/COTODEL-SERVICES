package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.QualificationDao;
import com.cotodel.hrms.auth.server.model.QualificationEntity;
import com.cotodel.hrms.auth.server.repository.QualificationRepository;
@Repository
public class QualificationDaoImpl implements QualificationDao{

	@Autowired
	QualificationRepository qualificationRepository;
	
	@Override
	public QualificationEntity saveDetails(QualificationEntity qualificationEntity) {
		
		return qualificationRepository.saveAndFlush(qualificationEntity);
		
	}


	@Override
	public List<QualificationEntity> getQualification(Long emplrid) {
		// TODO Auto-generated method stub
		return qualificationRepository.findByQualificationId(emplrid);
	}

	

}
