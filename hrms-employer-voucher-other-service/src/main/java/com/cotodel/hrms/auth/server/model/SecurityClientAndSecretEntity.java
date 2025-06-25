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
@Table(name = "security_client_secret", uniqueConstraints = @UniqueConstraint(columnNames = {"client_id"}) )
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="security_client_secret_seq" , sequenceName="security_client_secret_seq", allocationSize=1)
public class SecurityClientAndSecretEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="security_client_secret_seq")
	@Column(name="id")
	private Long id;
		
	@Column(name="bankcode", length=99)
	private String bankCode;//FK	
	
	@Column(name="client_id")
	private String clientId;
	
	@Column(name="secret_id")
	private String secretId;
	
	@Column(name="status")
	private int status;
	
}
