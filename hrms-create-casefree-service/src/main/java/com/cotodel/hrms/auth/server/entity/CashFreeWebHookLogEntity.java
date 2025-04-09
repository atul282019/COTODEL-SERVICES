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
@Table(name = "cash_free_web_hook_log")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="cash_free_web_hook_log_seq" , sequenceName="cash_free_web_hook_log_seq", allocationSize=1)
public class CashFreeWebHookLogEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cash_free_web_hook_log_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name = "creationdate")
	private LocalDateTime creationDate;
	
	@Column(name="order_id")
	private String orderId;	
		
	@Column(name="response_json", columnDefinition = "TEXT")
	private String responseJson;
	
}
