package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
@Table(name="erupi_voucher_txn_details")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="erupi_voucher_txn_details_seq" , sequenceName="erupi_voucher_txn_details_seq", allocationSize=1)
public class ErupiVoucherTxnDetailsEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="erupi_voucher_txn_details_seq")
	@Column(name="id_pk")
	private Long id;
	
	@Column(name="details_id")
	private Long detailsId;//bigint	id_pk of issuancedetails
	
	@Column(name="workflowid")
	private Long workFlowId;//bigint FK	Work FlowID 100001 for initiate reuest, 100002 for creation,100003 for fail,100004 for confirmationpending, 100005 for redemption

	//	@ManyToOne
//	@JoinColumn(name = "wokflowjoinid", referencedColumnName = "workflowid")  // foreign key column
//	private WorkFlowMasterEntity wokflowId;//id_pk of vouchermaster
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
	
	@Column(name="voucher_type", length=49)
	private String voucherType;
	
	@Column(name="uuid", length=99)
	private String uuid;

	@Column(name="umn", length=99)
	private String umn;
	
	@Column(name="merchanttxnid", length=99)
	private String merchanttxnId;
	
	@Column(name="result_call_api", length=99)
	private String resultCallApi;
	
	@Column(name="status_api", length=99)
	private String statusApi;
	
	@Column(name="sms_response", length=99)
	private String smsResponse;
	
	@Column(name="sms_actcode", length=99)
	private String smsActcode;
	
	@Column(name="sms_desc", length=99)
	private String smsDesc;
	     
	@Column(name="api_type", length=99)
	private String apiType;
	
	@Column(name="createdby", length=99)
	private String createdBy;
	
	@Column(name="mcc", length=99)
	private String mcc;
	
	@Column(name="bankrrn", length=99)
	private String bankrrn;
	
	@Column(name="txnstatus", length=99)
	private String txnstatus;
	
	@Column(name = "txninitdate")
	private LocalDateTime txninitdate;
	
	@Column(name = "txncompletiondate")
	private LocalDateTime txncompletiondate;
	
	@Column(name="extra", length=99)
	private String extra;
	
	@Column(name="extra1", length=99)
	private String extra1;
	
	@Column(name="extra2", length=99)
	private String extra2;
	    
}
