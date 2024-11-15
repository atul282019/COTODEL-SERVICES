package com.cotodel.hrms.auth.server.repository;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.model.ErupiBankMasterEntity;
@Repository
public interface BankMasterRepository extends JpaRepository<ErupiBankMasterEntity,Long>{
	
	  @Query("select s  from ErupiBankMasterEntity s where s.bankCode = ?1")
	  public ErupiBankMasterEntity findByBankCode(String  bankcode);
	
		@Modifying
	    @Transactional
	    @Query(value = "UPDATE erupi_bankmaster  SET status=:status WHERE id_pk =:id", nativeQuery = true)
	    public int updateActiveStatus(@Param("id") Long id,@Param("status") int status);
}
