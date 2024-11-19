package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name="erupi_voucher_creation_details")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="erupi_voucher_creation_details_seq" , sequenceName="erupi_voucher_creation_details_seq", allocationSize=1)
public class ErupiVoucherCreationDetailsEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="erupi_voucher_creation_details_seq")
	@Column(name="id_pk")
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "voucher_id_pk", referencedColumnName = "id_pk")  // foreign key column
	  private VoucherTypeMasterEntity voucherId;//id_pk of vouchermaster
	
	@Column(name="name", length=99)
	private String name;
	
	@Column(name="mobile", length=11)
	private String mobile;
			
	@Column(name="amount")
	private Float amount;	 	
	
	@Column(name = "startdate")
    private LocalDate startDate;
			
	@Column(name = "expdate")
    private LocalDate expDate;
	
	@Column(name = "purposecode", length =29 )
    private String purposeCode;
	
	@Column(name = "consent", length =49 )
    private String consent;
	
	@Column(name = "otp_validation_status", length =9 )
    private String otpValidationStatus;
		 	
	@Column(name = "creationdate")
    private LocalDate creationDate;
	

	@Column(name="createdby", length=49)
	private String createdby;
	
	@Column(name="account_id")
	private Long accountId;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="accountnumber",length = 49)
	private String accountNumber;
	
	@Column(name="workflowid")
	private Long workFlowId;
	
	
//	@Column(name="entrymode_id_pk",length = 29)
//	private String entryModeId;//entrymode master id_pk value
			
	@ManyToOne
	@JoinColumn(name = "entrymode_id_pk", referencedColumnName = "id_pk")  // foreign key column
	private EntryModeMasterEntity entrymodeIdPk;//id_pk of vouchermaster		

	@Column(name="merchanttxnid",length = 99)
	private String merchanttxnid;
	@Column(name="creationmode",length = 49)
	private String creationmode;
	
	@Column(name="bulktbl_id")
	private Long bulktblId;

	@Column(name="redemtion_type")
	private String redemtionType;
	
	@Column(name="mcc")
	private String mcc;
	
	@Column(name="extra1")
	private String extra1;
	
	@Column(name="extra2")
	private String extra2;
	
	@Column(name="extra3")
	private String extra3;
	
	@Column(name="beneficiary_id")
	private String beneficiaryID;
	
	@Column(name="payer_va")
	private String payerVA;
	
	@Column(name="vouchercode", length=19)
	private String voucherCode;
	
	@Column(name="vouchertype", length=19)
	private String voucherType;
	
	@Column(name="voucherdesc", length=19)
	private String voucherDesc;
	
	@Column(name="merchantid", length=19)
	private String merchantId;
	@Column(name="submerchantid", length=19)
	private String subMerchantId;
	
}
