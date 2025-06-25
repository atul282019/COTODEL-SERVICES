package com.cotodel.hrms.auth.server.model.master;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="erupi_mcc_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="erupi_mcc_master_id_pk_seq" , sequenceName="erupi_mcc_master_id_pk_seq", allocationSize=1)
public class MccMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="erupi_mcc_master_id_pk_seq")
	@Column(name="id_pk")
	private Long id;	
	
	@Column(name="purpose_code")
	private String purposeCode;
	
	@Column(name="purpose_desc")
	private String purposeDesc;	
	
	@Column(name="mcc")
	private String mcc;
	
	@Column(name="mcc_desc")
	private String mccDesc;
	
	@Column(name="status")
	private int status;
	
	@Column(name="voucher_name")
	private String voucherName;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name = "voucher_icon")
    private byte[] voucherIcon;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name = "mcc_icon")
    private byte[] mccIcon;
	
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name = "mcc_main_icon")
    private byte[] mccMainIcon;
	
	@Column(name="pr_status")
	private int prStatus;
}
