package com.cotodel.hrms.auth.server.repository;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;

import com.cotodel.hrms.auth.server.entity.DatabaseMaster;

public interface DatabaseMasterRepository extends JpaRepository<DatabaseMaster, Long> {

}
