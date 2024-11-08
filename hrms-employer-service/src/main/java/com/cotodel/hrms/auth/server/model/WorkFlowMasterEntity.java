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
@Table(name="WORKFLOWMASTER")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="WORKFLOWMASTER_seq" , sequenceName="WORKFLOWMASTER_seq", allocationSize=1)
public class WorkFlowMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="WORKFLOWMASTER_seq")
	@Column(name="id_pk")
	private Long id;
	
	@Column(name="workflowid")
	private Long workflowId;//workflowid	biginteger	Work FlowID 100001 for initiate reuest, 100002 for creation,100003 for fail,100004 for confirmationpending, 100005 for redemption
	
	@Column(name="description", length=99)
	private String description;
	
	@Column(name="type", length=9)
	private String type;
	
	@Column(name="api_url", length=299)
	private String apiurl;
	
	@Column(name="public_ip", length=29)
	private String publicip;
	
	@Column(name="status")
	private int status;	
	
}
