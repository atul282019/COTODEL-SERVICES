package com.cotodel.hrms.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotodel.hrms.auth.server.entity.UserEmpEntity;

public interface UserEmpRepository extends JpaRepository<UserEmpEntity, Long> {

}
