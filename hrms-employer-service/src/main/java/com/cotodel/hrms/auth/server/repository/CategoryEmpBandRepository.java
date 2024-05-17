package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.CategoryEmployeeBandEntity;
@Repository
public interface CategoryEmpBandRepository extends JpaRepository<CategoryEmployeeBandEntity,Long>{
	

	
}
