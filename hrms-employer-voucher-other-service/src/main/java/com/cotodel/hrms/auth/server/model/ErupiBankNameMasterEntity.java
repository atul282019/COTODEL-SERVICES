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
@Table(name="erupi_banknamemaster")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="erupi_banknamemaster_id_pk_seq" , sequenceName="erupi_banknamemaster_id_pk_seq", allocationSize=1)
public class ErupiBankNameMasterEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="erupi_banknamemaster_id_pk_seq")
	@Column(name="id_pk")
	private Long id;
	
	@Column(name="sno", length=9)
	private String sno;
	
	@Column(name="bankname", length=99)
	private String bankname;
	
	@Column(name="bankcode", length=9)
	private String bankcode;
	
	@Column(name="ifsc", length=4)
	private String ifsc;
	
	private int status;
	
}
