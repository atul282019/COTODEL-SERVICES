package com.cotodel.hrms.auth.server.model;

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
import javax.persistence.UniqueConstraint;

import com.cotodel.hrms.auth.server.util.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "link_sub_account_multiple", uniqueConstraints = @UniqueConstraint(columnNames = {"acnumber"}) )
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="link_sub_account_multiple_seq" , sequenceName="link_sub_account_multiple_seq", allocationSize=1)
public class LinkSubAccountMultipleEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="link_sub_account_multiple_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="link_id")
	private Long linkId;
	
	@Column(name="bankcode", length=99)
	private String bankCode;//FK
	
	@Column(name="bankname", length=99)
	private String bankName;
	
	@Column(name="accountholdername", length=99)
	private String accountHolderName;
	
	@Column(name="acnumber", length=18,unique = true)
	private String acNumber;
	
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
	
	@Column(name = "approved_date")
	private LocalDateTime approvedDate;
	
	@Column(name="approved_by", length=49)
	private String approvedby;
	
	@Column(name = "rejected_date")
	private LocalDateTime rejectedDate;
	
	@Column(name="rejected_by", length=49)
	private String rejectedby;
}
