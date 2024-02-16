package com.cotodel.hrms.auth.server.repository;

/**
 * @author vinay
 */
import org.springframework.data.jpa.repository.JpaRepository;

import com.cotodel.hrms.auth.server.entity.LicenceMaster;

public interface LicenceMasterRepository extends JpaRepository<LicenceMaster, Long> {

}
