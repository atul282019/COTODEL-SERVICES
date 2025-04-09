package com.cotodel.hrms.auth.server.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cotodel.hrms.auth.server.util.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "link_sub_account_multiple_temp")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="link_sub_account_multiple_temp_seq" , sequenceName="link_sub_account_multiple_temp_seq", allocationSize=1)
public class LinkSubAccountMultipleTempEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="link_sub_account_multiple_temp_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="bankcode", length=99)
	private String bankCode;//FK
	
	@Column(name="bankname", length=99)
	private String bankName;
	
	@Column(name="accountholdername", length=99)
	private String accountHolderName;
	
	@Column(name="acnumber", length=18)
	private String acNumber;
	
	@Column(name="order_id")
	private String orderId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="accounttype", length=99)
	private AccountType accountType;//Saving, Current
	
	@Column(name="ifsc_code", length=99)
	private String ifscCode;
	
	@Column(name = "creationdate")
	private LocalDateTime creationDate;
	
	@Column(name="createdby", length=49)
	private String createdby;
		
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="mobile", length=11)
	private String mobile;
	
	@Column(name="status")
	private Long status;
	
	@Column(name="amount_limit")
	private Float amountLimit;
	
	@Column(name="balance")
	private Float balance;
	
	@Column(name="service_charge")
	private Float serviceCharge;
	
	@Column(name="service_tax")
	private Float serviceTax;
	
	@Column(name = "approved_date")
	private LocalDateTime approvedDate;
	
	@Column(name="approved_by", length=49)
	private String approvedby;
	
	@Column(name = "rejected_date")
	private LocalDateTime rejectedDate;
	
	@Column(name="rejected_by", length=49)
	private String rejectedby;
	
	@Column(name="status_message")
	private String statusMessage;
}
