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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="erupi_bankmaster")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="erupi_bankmaster_seq" , sequenceName="erupi_bankmaster_seq", allocationSize=1)
public class ErupiBankMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="erupi_bankmaster_seq")
	@Column(name="id_pk")
	private Long id;
	
	@Column(name="bankcode", length=99)
	private String bankCode;
	
	@Column(name="bankname", length=99)
	private String bankName;
	
	@Column(name="status")
	private int status;		
	
	@Column(name = "creationdate")
    private LocalDateTime creationDate;

	
	
}
