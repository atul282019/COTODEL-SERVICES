package com.cotodel.hrms.auth.server.model;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="erupi_voucher_request")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="erupi_voucher_request_seq" , sequenceName="erupi_voucher_request_seq", allocationSize=1)
public class ErupiVoucherCreationRequestEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="erupi_voucher_request_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="employer_id")
	private Long employerId;
	
	@Column(name="employee_id")
	private Long employeeId;
	
	@Column(name="name", length=99)
	private String name;
	
	@Column(name="mobile", length=10)
	private String mobile;
	
	@Column(name="amount")
	private Float amount;
	
	@Column(name="voucher_type", length=99)
	private String voucherType;
	
	@Column(name="voucher_sub_type", length=99)
	private String voucherSubType;
	
	@Column(name="status")
	private int status;
	
	@Column(name="status_message")
	private String statusMessage;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name = "creation_date")
    private LocalDateTime creationDate;

}
