package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="erupi_voucher_txn")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="erupi_voucher_txn_seq" , sequenceName="erupi_voucher_txn_seq", allocationSize=1)
public class ErupiVoucherTxnEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="erupi_voucher_txn_seq")
	@Column(name="id_pk")
	private Long id;
	
	@Column(name="details_id")
	private Long detailsId;//bigint	id_pk of issuancedetails
	
	@Column(name="workflowid")
	private Long workFlowId;//bigint FK	Work FlowID 100001 for initiate reuest, 100002 for creation,100003 for fail,100004 for confirmationpending, 100005 for redemption
	
//	@Column(name="voucher_id")
//	private Long voucherId;//id_pk of vouchermaster
	
	@ManyToOne
	 @JoinColumn(name = "voucher_id_pk", referencedColumnName = "id_pk")  // foreign key column
	  private VoucherTypeMasterEntity voucherId;//id_pk of vouchermaster
	
	@Column(name = "creationdate")
	private LocalDateTime creationDate;
	
	@Column(name="response", length=199)
	private String response;
	
	@Column(name="response_code", length=99)
	private String responseCode;
	
	@Column(name="account_id")
	private Long accountId;
	
	@Column(name="org_id_pk")
	private Long orgIdPk;


	
}
