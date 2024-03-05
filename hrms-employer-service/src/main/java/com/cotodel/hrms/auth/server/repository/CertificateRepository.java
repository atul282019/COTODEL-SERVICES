package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.CertificateEntity;
@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity,Long>{
	
	@Query("select s  from CertificateEntity s where s.employeeId = ?1")
	public List<CertificateEntity> findByCertificateId(Long emplid);
}
