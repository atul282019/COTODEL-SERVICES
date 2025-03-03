package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;

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
@Table(name = "transaction_history")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="transaction_history_seq" , sequenceName="transaction_history_seq", allocationSize=1)
public class TransactionHistoryEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="transaction_history_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="link_sub_id")
	private Long linkSubId;
	
	@Column(name="acnumber", length=18)
	private String acNumber;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="mobile", length=11)
	private String mobile;
	
	@Column(name="amount_cr")
	private Float amountCR;//revoke
	
	@Column(name="amount_dr")
	private Float amountDr;//transaction
	
	@Column(name="merchantid", length=19)
	private String merchantId;
	
	
}