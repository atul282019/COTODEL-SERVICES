package com.cotodel.hrms.auth.server.entity;


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
@Table(name="otp_logs")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="otp_logs_seq" , sequenceName="otp_logs_seq", allocationSize=1)
public class OtpLogEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="otp_logs_seq")
	private Long id ;
    @Column(name = "mobile")
    private String  mobile ;
    @Column(name = "response")
    private String  response ;
    @Column(name = "flag")
    private String  flag ;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    
}
