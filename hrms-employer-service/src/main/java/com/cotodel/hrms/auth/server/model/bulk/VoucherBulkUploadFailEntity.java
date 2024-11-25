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
@Table(name="voucher_bulk_upload_fail")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="voucher_bulk_upload_fail_seq" , sequenceName="voucher_bulk_upload_fail_seq", allocationSize=1)
public class VoucherBulkUploadFailEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="voucher_bulk_upload_fail_seq")
	private Long id;
	
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name = "creation_date")
    private LocalDate creationDate;
	
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
	
	@Column(name="message")
	private String message;
	
	@Column(name = "org_id")
    private Long orgId;
}
