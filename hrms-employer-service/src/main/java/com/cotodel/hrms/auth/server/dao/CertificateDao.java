package com.cotodel.hrms.auth.server.dao;

import java.util.List;

import com.cotodel.hrms.auth.server.model.CertificateEntity;

public interface CertificateDao {
	public CertificateEntity saveDetails(CertificateEntity certificateEntity);
	public List<CertificateEntity> getCertificate(Long emplrid);
}
