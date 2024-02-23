package com.cotodel.hrms.auth.server.entity;

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
@Table(name="payroll_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="payroll_master_seq" , sequenceName="payroll_master_seq", allocationSize=1)
public class PayrollMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="payroll_master_seq")
	@Column(name="id")
	private Long id;
	
	private String salary_component;
    private String per_ctc;
    private String per ;
    private String  taxable;
    private Integer status;
    
}
