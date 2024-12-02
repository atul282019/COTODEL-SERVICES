package com.cotodel.hrms.auth.server.model.bulk;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="voucher_bulk_upload")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="voucher_bulk_upload_seq" , sequenceName="voucher_bulk_upload_seq", allocationSize=1)
public class VoucherBulkUploadEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="voucher_bulk_upload_seq")
	private Long id;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name = "file")
    private byte[] file;
	
	@Column(name="total_count")
	private String totalCount;
	
	@Column(name="success_count")
	private String successCount;
	
	@Column(name="fail_count")
	private String failCount;
	
	@Column(name = "creation_date")
    private LocalDate creationDate;
	
	@Column(name = "file_name")
    private String fileName;
	
	@Column(name = "org_id")
    private Long orgId;
	
	@Column(name = "purposecode", length =29 )
    private String purposeCode;
	

	@Column(name="createdby", length=49)
	private String createdby;
	
	@Column(name="account_id")
	private Long accountId;
	
	@Column(name="accountnumber",length = 49)
	private String accountNumber;
		
	@Column(name="bulktbl_id")
	private Long bulktblId;

	@Column(name="redemtion_type")
	private String redemtionType;
	
	@Column(name="mcc")
	private String mcc;
	
	@Column(name="beneficiary_id")
	private String beneficiaryID;
	
	@Column(name="payer_va")
	private String payerVA;
	
	@Column(name="vouchercode", length=19)
	private String voucherCode;
	
	@Column(name="voucherdesc", length=19)
	private String voucherDesc;
	
	@Column(name="merchantid", length=19)
	private String merchantId;
	
	@Column(name="submerchantid", length=19)
	private String subMerchantId;
	
	@Column(name="type")
	private String type;
	
	@Column(name="bankcode")
	private String bankcode;
	
	@Column(name="mandate_type")
	private String MandateType;
	
	@Column(name="payee_vpa")
	private String PayeeVPA;
}
