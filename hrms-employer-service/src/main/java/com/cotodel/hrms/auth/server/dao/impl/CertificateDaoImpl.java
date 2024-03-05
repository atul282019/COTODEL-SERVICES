package com.cotodel.hrms.auth.server.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.CertificateDao;
import com.cotodel.hrms.auth.server.model.CertificateEntity;
import com.cotodel.hrms.auth.server.repository.CertificateRepository;
@Repository
public class CertificateDaoImpl implements CertificateDao{

	@Autowired
	CertificateRepository certificateRepository;

	@Override
	public CertificateEntity saveDetails(CertificateEntity certificateEntity) {
		// TODO Auto-generated method stub
		return certificateRepository.saveAndFlush(certificateEntity);
	}

	@Override
	public List<CertificateEntity> getCertificate(Long emplrid) {
		// TODO Auto-generated method stub
		return certificateRepository.findByCertificateId(emplrid);
	}


	
	
	
	

}
