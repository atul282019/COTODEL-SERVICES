package com.cotodel.hrms.auth.server.entity;


import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="repute_token_details", uniqueConstraints = @UniqueConstraint(columnNames = "mobile"))
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="repute_token_details_seq" , sequenceName="repute_token_details_seq", allocationSize=1)
public class ReputeTokenEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="repute_token_details_seq")
	private Long id ;
	
    @Column(name="mobile",unique=true,nullable = false)
    private String  mobile ;
    
    @Column(name="created_date")
	private LocalDateTime createdDate;
    
    @Column(name="access_token",columnDefinition = "TEXT")
	private String accessToken;
    
    @Column(name="refresh_token")
	private String refreshToken;
    
    @Column(name="scope")
	private String scope;
    
    @Column(name="id_token",columnDefinition = "TEXT")
	private String idToken;
    
    @Column(name="token_type")
	private String tokenType;
    
    @Column(name="expires_in")
	private String expiresIn;
            
}
