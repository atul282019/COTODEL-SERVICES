package com.cotodel.hrms.auth.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.CashFreeOrderHistory;
import com.cotodel.hrms.auth.server.entity.CashFreeOrderEntity;

@Repository
public interface CashFreeOrderRepository extends JpaRepository<CashFreeOrderEntity,Long>{
	
	@Query("select s  from CashFreeOrderEntity s where s.customerId = ?1")
	public CashFreeOrderEntity findBycustomerId(String userid);
	
	@Query("select s  from CashFreeOrderEntity s where s.orgId = ?1")
	public List<CashFreeOrderEntity> findByOrgId(Long orgId);
	
	@Query("SELECT new com.cotodel.hrms.auth.server.dto.CashFreeOrderHistory(o.id,o.orderAmount,o.orderCurrency,o.customerId,"
			+ "o.customerName,o.customerEmail,o.customerPhone,o.orderId,o.orderStatus,o.cfPaymentId,o.paymentStatus,o.paymentAmount,o.paymentCurrency,"
			+ "o.paymentMessage,o.paymentTime,o.bankReference,o.paymentGroup,w.errorCode,w.errorDescription,w.errorReason,w.gatewayName,"
			+ "w.gatewayOrderId,w.gatewayPaymentId,w.gatewayOrderReferenceId,w.eventTime,w.type,w.serviceCharge,w.serviceTax,"
			+ "w.settlementAmount,w.settlementCurrency,w.serviceChargeDiscount) " +
		       "FROM CashFreeOrderEntity o " +
		       "LEFT JOIN CashFreeOrderWebHookEntity w ON o.orderId = w.orderId " +
		       "WHERE o.orgId = ?1 order by o.id desc ")
		List<CashFreeOrderHistory> findCashFreeOrderHistory(Long orgId);
	
//	@Query("select s  from CashFreeOrderEntity s where s.orgId =:orgId and s.createdDate BETWEEN :startOfMonth AND :endOfMonth")
//	public List<CashFreeOrderEntity> findByAmountCurrentMonthOrgId(@Param("orgId") Long orgId);
}


	
 
  	
  	
  
  
  