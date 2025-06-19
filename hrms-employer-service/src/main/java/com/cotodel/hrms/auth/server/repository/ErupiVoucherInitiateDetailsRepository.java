package com.cotodel.hrms.auth.server.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dto.AccountWiseAmountDTO;
import com.cotodel.hrms.auth.server.dto.AccountWiseAmountQueryDTO;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseDto;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto;
import com.cotodel.hrms.auth.server.dto.PurposeCodeAmountDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemDto;
import com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemTransactionDto;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationDetailsEntity;
@Repository
public interface ErupiVoucherInitiateDetailsRepository extends JpaRepository<ErupiVoucherCreationDetailsEntity,Long>{
	
	@Modifying
    @Transactional
    @Query(value = "UPDATE erupi_voucher_creation_details  SET workflowid =:workflowid WHERE id_pk =:id", nativeQuery = true)
    public int updateWorkflowId(@Param("id") Long id,@Param("workflowid") Long workflowid);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto(c.id,c.name,c.mobile,c.amount,"
			+ "t.merchanttxnId,c.purposeCode,c.mcc,c.redemtionType,c.creationDate,c.expDate,w.type,"
			+ "c.voucherCode,m.purposeDesc,m.mccDesc,c.accountNumber,c.bankcode,m.voucherIcon) "
			+ "from ErupiVoucherCreationDetailsEntity c"
			+ " join ErupiVoucherTxnDetailsEntity t on c.id = t.detailsId and t.workFlowId = c.workFlowId "
			+ " join WorkFlowMasterEntity w on c.workFlowId=w.workflowId"
			+ " join MccMasterEntity m on m.mcc=c.mcc and  c.purposeCode=m.purposeCode  where   c.orgId =:orgId "
			+ " and c.creationDate BETWEEN :startDate and :endDate   order by c.creationDate desc ")
	public List<ErupiVoucherCreatedDto> findVoucherCreateList(@Param("orgId") Long orgId,    
	        @Param("startDate") LocalDate startDate, 
	        @Param("endDate") LocalDate endDate);
	
//	 @Query(value = "SELECT count(1), SUM(amount), " +
//             "(SELECT voucherdesc FROM voucher_type_master c WHERE c.id_pk = a.voucher_id_pk) AS vname " +
//             "FROM erupi_voucher_creation_details a, erupi_voucher_txn_details b " +
//             "WHERE a.id_pk = b.details_id AND b.workflowid ='100003' AND a.org_id =:orgId " +
//             "GROUP BY a.voucher_id_pk", nativeQuery = true)
	@Query(value = "SELECT count (1),(SELECT type FROM hrms1.workflowmaster w WHERE w.workflowid=b.workflowid) as type ,"
			+ " SUM(amount),(SELECT voucherdesc FROM hrms1.voucher_type_master c WHERE c.id_pk=a.voucher_id_pk) as vname FROM hrms1.erupi_voucher_creation_details a, hrms1.erupi_voucher_txn_details b "
			+ "WHERE a.id_pk=b.details_id and b.workflowid in ('100003','100005','100007') and a.org_id=:orgId "
			+ "GROUP BY a.voucher_id_pk,b.workflowid ", nativeQuery = true)
	public List<Object[]> getVoucherSummary(@Param("orgId") Long orgId);
//	@Query(value = "SELECT count (1), SUM(amount),(SELECT voucher_name FROM hrms1.erupi_mcc_master c WHERE c.mcc=a.mcc) as vname ,"
//			+ "(SELECT voucher_icon FROM hrms1.erupi_mcc_master c WHERE c.mcc=a.mcc) as voucherIcon"
//			+ " FROM hrms1.erupi_voucher_creation_details a, hrms1.erupi_voucher_txn_details b "
//			+ "WHERE a.id_pk=b.details_id and b.workflowid='100003' and a.org_id=:orgId "
//			+ "GROUP BY a.mcc", nativeQuery = true)
//	public List<Object[]> getVoucherCreateSummary(@Param("orgId") Long orgId);
	
	@Query(value = " SELECT COUNT(1) AS record_count, SUM(a.amount) AS total_amount,c.voucher_name AS vname,STRING_AGG(DISTINCT c.voucher_icon, ', ') AS voucherIcon "
			+ "	FROM hrms1.erupi_voucher_creation_details a JOIN hrms1.erupi_voucher_txn_details b "
			+ "	    ON a.id_pk = b.details_id	JOIN hrms1.erupi_mcc_master c ON c.mcc = a.mcc AND c.purpose_code = a.purposecode"
			+ "	WHERE b.workflowid = '100003'  AND a.org_id =:orgId	GROUP BY  c.voucher_name", nativeQuery = true)
	public List<Object[]> getVoucherCreateSummary(@Param("orgId") Long orgId);
	
	
//	@Query(value = "SELECT COUNT(1) AS record_count,SUM(a.amount) AS total_amount,b.workflowid,(SELECT w.type "
//			+ "FROM hrms1.workflowmaster w WHERE w.workflowid = b.workflowid) AS type,CASE WHEN a.expdate < CURRENT_DATE THEN 'Expire' "
//			+ "ELSE CASE WHEN a.workflowid=100003 THEN 'Active' ELSE 'InActive' END  status FROM hrms1.erupi_voucher_creation_details a JOIN "
//			+ "hrms1.erupi_voucher_txn_details b ON a.id_pk = b.details_id "
//			+ "WHERE a.org_id =:orgId and b.workflowid!=100004 GROUP BY b.workflowid,status", nativeQuery = true)
	@Query(value = "SELECT COUNT(1) AS record_count,SUM(a.amount) AS total_amount,b.workflowid,(SELECT w.type FROM hrms1.workflowmaster w "
			+ " WHERE w.workflowid = b.workflowid) AS type,CASE WHEN a.expdate < CURRENT_DATE and a.workflowid='100003' THEN 'expire' "
			+ " ELSE 'active' END AS status FROM hrms1.erupi_voucher_creation_details a JOIN  hrms1.erupi_voucher_txn_details b "
			+ " ON a.id_pk = b.details_id  WHERE a.org_id =:orgId and b.workflowid in ('100003','100005','100007') "
			+ "  GROUP BY b.workflowid,status", nativeQuery = true)
	public List<Object[]> getVoucherCreateStatus(@Param("orgId") Long orgId);	
	
	@Query("select s  from ErupiVoucherCreationDetailsEntity s where s.merchanttxnid = ?1")
	public ErupiVoucherCreationDetailsEntity findByTransactionId(String tranId);
	
	@Query(value = "select Distinct Upper(c.name) name,c.mobile mobile from hrms1.erupi_voucher_creation_details c where c.org_id=:orgId ", nativeQuery = true)
	public List<Object[]> findByNameByOrgId(@Param("orgId") Long orgId);
	
	@Query(value = "SELECT DISTINCT a.accountnumber,b.bankname,b.banklogo FROM hrms1.erupi_voucher_creation_details a JOIN "
			+ "	hrms1.erupi_bankmaster b ON a.bankcode = b.bankcode 	WHERE a.org_id =:orgId ", nativeQuery = true)
	public List<Object[]> getVoucherCreateBankList(@Param("orgId") Long orgId);
	
	@Query(value = "SELECT COUNT(1) AS record_count,SUM(a.amount) AS total_amount,b.workflowid,(SELECT w.type "
			+ "FROM hrms1.workflowmaster w WHERE w.workflowid = b.workflowid) AS type,CASE WHEN a.expdate < CURRENT_DATE and a.workflowid='100003' THEN 'expire' "
			+ "ELSE 'active' END AS status FROM hrms1.erupi_voucher_creation_details a JOIN "
			+ "hrms1.erupi_voucher_txn_details b ON a.id_pk = b.details_id "
			+ "WHERE a.org_id =:orgId and b.workflowid in ('100003','100005','100007') and a.accountnumber=:accNumber GROUP BY b.workflowid,status", nativeQuery = true)
	public List<Object[]> getVoucherCreateSummaryWithAccNo(@Param("orgId") Long orgId,@Param("accNumber") String accNumber);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto(c.id,c.name,c.mobile,c.amount,"
			+ "t.merchanttxnId,c.purposeCode,c.mcc,c.redemtionType,c.creationDate,c.expDate,w.type,"
			+ "c.voucherCode,m.purposeDesc,m.mccDesc,c.accountNumber,c.bankcode,m.voucherIcon) "
			+ "from ErupiVoucherCreationDetailsEntity c"
			+ " join ErupiVoucherTxnDetailsEntity t on c.id = t.detailsId and t.workFlowId = c.workFlowId "
			+ " join WorkFlowMasterEntity w on c.workFlowId=w.workflowId"
			+ " join MccMasterEntity m on m.mcc=c.mcc and  c.purposeCode=m.purposeCode  where   c.id =:id ")
	public ErupiVoucherCreatedDto findVoucherCreateById(@Param("id") Long id);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto(c.id,c.name,c.mobile,c.amount,"
			+ "t.merchanttxnId,c.purposeCode,c.mcc,c.redemtionType,c.creationDate,c.expDate,w.type,"
			+ "c.voucherCode,m.purposeDesc,m.mccDesc,c.accountNumber,c.bankcode,m.voucherIcon) "
			+ "from ErupiVoucherCreationDetailsEntity c"
			+ " join ErupiVoucherTxnDetailsEntity t on c.id = t.detailsId and t.workFlowId = c.workFlowId "
			+ " join WorkFlowMasterEntity w on c.workFlowId=w.workflowId"
			+ " join MccMasterEntity m on m.mcc=c.mcc and  c.purposeCode=m.purposeCode  where   c.orgId =:orgId "
			+ " and c.id =:id    order by c.creationDate desc ")
	public List<ErupiVoucherCreatedDto> findVoucherCreateListById(@Param("orgId") Long orgId,@Param("id") Long id);
	
	@Query("select new com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDateWiseDto(c.id,c.name,c.mobile,c.amount,"
			+ "t.merchanttxnId,c.purposeCode,c.mcc,c.redemtionType,c.creationDate,c.expDate,w.type,"
			+ "c.voucherCode,m.purposeDesc,m.mccDesc,c.accountNumber,c.bankcode) "
			+ "from ErupiVoucherCreationDetailsEntity c"
			+ " join ErupiVoucherTxnDetailsEntity t on c.id = t.detailsId and t.workFlowId = c.workFlowId "
			+ " join WorkFlowMasterEntity w on c.workFlowId=w.workflowId"
			+ " join MccMasterEntity m on m.mcc=c.mcc and  c.purposeCode=m.purposeCode  where    t.workFlowId!=100004 and"
			+ "  c.creationDate BETWEEN :startDate and :endDate and c.bankcode=:bankCode  order by c.creationDate desc ")
	public List<ErupiVoucherCreatedDateWiseDto> findVoucherCreateListDateWise(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate,@Param("bankCode") String bankCode);
	
	@Query(value = "SELECT a.accountnumber AS accountNumber,a.org_id AS orgId,SUM(a.amount) AS totalAmount,COALESCE(SUM(b.total_red), 0) AS redeemAmount "
			+ "FROM hrms1.erupi_voucher_creation_details a LEFT JOIN (SELECT details_id, SUM(CAST(payer_amount AS DECIMAL(10,2))) AS total_red "
			+ "FROM hrms1.erupi_voucher_txn_details   WHERE workflowid IN ('100003', '100007') GROUP BY "
			+ "details_id ) b ON a.id_pk = b.details_id WHERE a.org_id =:orgId AND a.workflowid IN ('100003', '100007')"
			+ "GROUP BY a.accountnumber,a.org_id",nativeQuery = true)	 
	public  List<Object[]> findTotalAmountGroupedByAccountAndOrg(@Param("orgId") Long orgId);
	 
	 @Query("SELECT new com.cotodel.hrms.auth.server.dto.PurposeCodeAmountDto(" +
		       "c.purposeCode, SUM(c.amount),m.voucherName) " +
		       "FROM ErupiVoucherCreationDetailsEntity c " +
		       "JOIN ErupiVoucherTxnDetailsEntity t ON c.id = t.detailsId AND t.workFlowId = c.workFlowId " +
		       "JOIN WorkFlowMasterEntity w ON c.workFlowId = w.workflowId " +
		       "JOIN MccMasterEntity m ON m.mcc = c.mcc AND c.purposeCode = m.purposeCode " +
		       "WHERE c.workFlowId != 100004 " +
		       "AND c.creationDate BETWEEN :startDate AND :endDate " +
		       "AND c.orgId = :orgId " +
		       "GROUP BY c.purposeCode, m.voucherName " +
		       "ORDER BY c.purposeCode")
		List<PurposeCodeAmountDto> getTotalAmountByPurposeCode(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate,
		                                                       @Param("orgId") Long orgId);
	 @Query("select new com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemDto(c.id,c.name,c.mobile,c.amount,"
				+ "t.merchanttxnId,c.purposeCode,c.mcc,c.redemtionType,c.creationDate,c.expDate,w.type,"
				+ "c.voucherCode,m.purposeDesc,m.mccDesc,c.accountNumber,c.bankcode,m.voucherIcon,COALESCE(t.payerAmount, '0'),m.voucherIcon) "
				+ "from ErupiVoucherCreationDetailsEntity c"
				+ " join ErupiVoucherTxnDetailsEntity t on c.id = t.detailsId and t.workFlowId = c.workFlowId "
				+ " join WorkFlowMasterEntity w on c.workFlowId=w.workflowId"
				+ " join MccMasterEntity m on m.mcc=c.mcc and  c.purposeCode=m.purposeCode  where   c.orgId =:orgId and c.workFlowId in ('100003','100004','100005','100007') "
				+ " and c.creationDate BETWEEN :startDate and :endDate   order by c.creationDate desc ")
		public List<ErupiVoucherCreatedRedeemDto> findVoucherCreateListLimit(@Param("orgId") Long orgId,    
		        @Param("startDate") LocalDate startDate, 
		        @Param("endDate") LocalDate endDate);
	 
	 @Query("select new com.cotodel.hrms.auth.server.dto.ErupiVoucherCreatedDto(c.id,c.name,c.mobile,c.amount,"
				+ "t.merchanttxnId,c.purposeCode,c.mcc,c.redemtionType,c.creationDate,c.expDate,w.type,"
				+ "c.voucherCode,m.purposeDesc,m.mccDesc,c.accountNumber,c.bankcode,m.voucherIcon) "
				+ "from ErupiVoucherCreationDetailsEntity c"
				+ " left join ErupiVoucherTxnDetailsEntity t on c.id = t.detailsId and t.workFlowId = c.workFlowId "
				+ " join WorkFlowMasterEntity w on c.workFlowId=w.workflowId"
				+ " join MccMasterEntity m on m.mcc=c.mcc and  c.purposeCode=m.purposeCode  where   c.orgId =:orgId "
				+ "   order by c.id desc ")
		public List<ErupiVoucherCreatedDto> findVoucherCreateTransactionList(@Param("orgId") Long orgId);
	 
	 @Query("select new com.cotodel.hrms.auth.server.dto.voucher.ErupiVoucherCreatedRedeemTransactionDto(c.id,c.name,c.mobile,c.amount,"
				+ "t.merchanttxnId,c.purposeCode,c.mcc,c.redemtionType,c.creationDate,c.expDate,w.type,"
				+ "c.voucherCode,m.purposeDesc,m.mccDesc,c.accountNumber,c.bankcode,m.voucherIcon,COALESCE(t.payerAmount, '0'),t.bankrrn,t.payeeName) "
				+ "from ErupiVoucherCreationDetailsEntity c"
				+ " join ErupiVoucherTxnDetailsEntity t on c.id = t.detailsId and t.workFlowId = c.workFlowId "
				+ " join WorkFlowMasterEntity w on c.workFlowId=w.workflowId"
				+ " join MccMasterEntity m on m.mcc=c.mcc and  c.purposeCode=m.purposeCode  where   c.orgId =:orgId and c.workFlowId='100007' "
				+ " and c.creationDate BETWEEN :startDate and :endDate   order by c.creationDate desc ")
		public List<ErupiVoucherCreatedRedeemTransactionDto> findVoucherCreateListRedeem(@Param("orgId") Long orgId,    
		        @Param("startDate") LocalDate startDate, 
		        @Param("endDate") LocalDate endDate);
	 
	 	@Query(value = "SELECT * from hrms1.view_voucher_summary1 where org_id=:orgId", nativeQuery = true)
		public List<Object[]> getVoucherCreateStatusView(@Param("orgId") Long orgId);
		@Query(value = "SELECT * from hrms1.view_voucher_summary1 where org_id=:orgId and accountnumber=:accountnumber", nativeQuery = true)
		public List<Object[]> getVoucherCreateStatusAccountView(@Param("orgId") Long orgId,@Param("accountnumber") String accountnumber);
}