package com.cotodel.hrms.auth.server.model.third;

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
@Table(name="third_party_validation")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="third_party_validation_seq" , sequenceName="third_party_validation_seq", allocationSize=1)
public class ThirdPartyValidationEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="third_party_validation_seq")
	@Column(name="id_pk")
	private Long id;	
	
	
	@Column(name="client_name")
	private String clintName;
	
	@Column(name="client_code")
	private String clientCode;
	
	@Column(name="secret_key")
	private String secretKey;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Column(name="status")
	private int status;
	
	
}
