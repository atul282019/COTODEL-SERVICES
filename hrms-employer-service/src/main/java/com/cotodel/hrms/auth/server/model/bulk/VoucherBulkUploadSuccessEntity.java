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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="voucher_bulk_upload_success")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="voucher_bulk_upload_success_seq" , sequenceName="voucher_bulk_upload_success_seq", allocationSize=1)
public class VoucherBulkUploadSuccessEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="voucher_bulk_upload_success_seq")
	private Long id;
	
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="voucher_type")
	private String voucherType;
	
	@Column(name="beneficiary_name")
	private String beneficiaryName;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="amount")
	private String amount;
	
	@Column(name = "start_date")
    private LocalDate startDate;
	
	@Column(name = "exp_date")
    private LocalDate expDate;

	@Column(name = "create_flag")
    private String createFlag="N";
	
	@Column(name = "org_id")
    private Long orgId;
	
	@Column(name = "purposecode", length =29 )
    private String purposeCode;
	
	@Column(name = "creation_date")
    private LocalDate creationDate;
	
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
	
	@Column(name="type", length=19)
	private String type;
	
	@Column(name="bankcode")
	private String bankcode;
	
	@Column(name="status")
	private Long status;
}
