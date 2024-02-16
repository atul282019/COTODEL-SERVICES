package com.cotodel.hrms.auth.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.BenefitsEntity;

@Repository
public interface BenefitsRepository extends JpaRepository<BenefitsEntity, Long>{
	
}
