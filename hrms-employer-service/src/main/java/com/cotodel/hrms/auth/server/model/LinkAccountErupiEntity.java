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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="linkaccount_erupi", indexes = {
	    @Index(name = "idx_employecode", columnList = "employecode")})
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="linkaccount_erupi_seq" , sequenceName="linkaccount_erupi_seq", allocationSize=1)
public class LinkAccountErupiEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="linkaccount_erupi_seq")
	@Column(name="id_pk")
	private Long id;
	
	
	 @ManyToOne
	 @JoinColumn(name = "bank_id", referencedColumnName = "id_pk")  // foreign key column
	  private BankMasterEntity bank;
	
	@Column(name="bankcode", length=99)
	private String bankCode;//FK
	
	@Column(name="bankname", length=99)
	private String bankName;
	
	@Column(name="accountholdername", length=99)
	private String accountHolderName;
		
	@Column(name="acnumber", length=99)
	private String acNumber;
	
	@Column(name="conirm_accnumber", length=99)
	private String conirmAccNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name="accounttype", length=99)
	private AccountType accountType;//Saving, Current
	
	@Column(name="ifsc", length=99)
	private String ifsc;
	
	@Column(name="erupi_flag", length=19)
	private String erupiFlag;//Y for enabled,N for Not enabled Default is null

	@Column(name = "creationdate")
	private LocalDateTime creationDate;
	
	@Column(name="createdby", length=49)
	private String createdby;
	
	@Column(name = "updatedate")
	private LocalDateTime updateDate;
	
	@Column(name="updatedby", length=49)
	private String updatedby;
		
	@Column(name="branchcode", length=19)
	private String branchCode;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="orgcode", length=19)
	private String orgCode;

	@Column(name="employecode", length=49)
	private String employeCode;
			
	@Column(name="auth_status", length=9)
	private String authStatus;	//Y for Yes default value is null
	
	@Column(name="auth_response", length=199)
	private String authResponse;
	
	@Column(name="mobile", length=11)
	private String mobile;
	
	@Column(name="accstatus")
	private Long accstatus;//Flag value 1 for Active and 0 for inactive Default is null
	//Remarks:big int default value value is 0
	
	@Column(name="tid", length=99)
	private String tid;
	
	@Column(name="mid", length=99)
	private String mid;	
	
	@Column(name="extra1", length=99)
	private String extra1;
	
	
	
	
}
