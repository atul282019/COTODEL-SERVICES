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
import javax.persistence.UniqueConstraint;

import com.cotodel.hrms.auth.server.util.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "erupi_linkaccount", uniqueConstraints = @UniqueConstraint(columnNames = {"acnumber"}) )
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="erupi_linkaccount_seq" , sequenceName="erupi_linkaccount_seq", allocationSize=1)
public class ErupiLinkAccountEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="erupi_linkaccount_seq")
	@Column(name="id_pk")
	private Long id;
	
	
	 @ManyToOne
	 @JoinColumn(name = "bank_id", referencedColumnName = "id_pk")  // foreign key column
	  private ErupiBankMasterEntity bank;
	
	@Column(name="bankcode", length=99)
	private String bankCode;//FK
	
	@Column(name="bankname", length=99)
	private String bankName;
	
	@Column(name="accountholdername", length=99)
	private String accountHolderName;
	
	@Column(name="acnumber", length=99,unique = true)
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
	
	@Column(name="merchentid", length=99)
	private String merchentIid;	
	
	@Column(name="extra1", length=99)
	private String extra1;
	
	@Column(name="mcc", length=99)
	private String mcc;
	
	@Column(name="submurchentid", length=99)
	private String submurchentid;
	 
	@Column(name="payerva", length=99)
	private String payerva;
	
	@Column(name="ps_flag", length=9)
	private String psFlag;//Flag value P for primary and S for Secondry Default is null
	
	@Column(name="corpmobno")
	private String corpmobno;
	
	@Column(name="corpupi_id")
	private String corpupiId;
	
	@Column(name="corpacc_no")
	private String corpaccNo;
	
	@Column(name="corpacc_type")
	private String corpaccType;
	
	@Column(name="corpifsc")
	private String corpifsc;
	
	@Column(name="corpname")
	private String corpname;
	
	@Column(name="benemail_id")
	private String benemailId;
	
	@Column(name="bene_id_name")
	private String beneIdName;
	
	@Column(name="bene_id_no")
	private String beneIdno;
	
	@Column(name="revocable")
	private String revocable;
	
	@Column(name="initiation_mode")
	private String initiationMode;
	
	@Column(name="account_balance")
	private Float accountBalance;
}
