package com.cotodel.hrms.auth.server.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="voucher_type_master", uniqueConstraints = @UniqueConstraint(columnNames = {"vouchercode"}))
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="voucher_type_master_seq" , sequenceName="voucher_type_master_seq", allocationSize=1)
public class VoucherTypeMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="voucher_type_master_seq")
	@Column(name="id_pk")
	private Long id;
	
	@Column(name="vouchercode", length=19,unique = true)
	private String voucherCode;
	
	@Column(name="vouchertype", length=99)
	private String voucherType;
	
	@Column(name="vouchersubtype", length=99)
	private String voucherSubType;
	
	@Column(name="voucherdesc", length=99)
	private String voucherDesc;
	
	@Column(name="purposecode", length=19)
	private String purposeCode;
	
	@Column(name="active_status", length=19)
	private String activeStatus;
		
	@Column(name = "creationdate")
    private LocalDateTime creationDate;
	
	@Column(name="createdby", length=99)
	private String createdby;
	
	@Column(name="status")
	private Long status;//Flag value for active/inactive
		
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name = "voucherlogo")
    private byte[] voucherLogo;
		
	
	

	
	
}
