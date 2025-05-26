package com.cotodel.hrms.auth.server.model.vehicle;

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
@Table(name="rc_log")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="rc_log_seq" , sequenceName="rc_log_seq", allocationSize=1)
public class RCLogEntity  implements Serializable{
	
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="rc_log_seq")
	@Column(name="id")
	private Long id;
		
	@Column(name="response")
	private String response;
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="status")
	private String status;
		
}
