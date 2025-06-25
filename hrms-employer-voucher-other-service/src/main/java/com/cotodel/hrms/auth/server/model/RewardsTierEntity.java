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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cotodel.hrms.auth.server.util.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rewards_tier")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="rewards_tier_seq" , sequenceName="rewards_tier_seq", allocationSize=1)
public class RewardsTierEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="rewards_tier_seq")
	@Column(name="id")
	private Long id;
	
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="tir_id")
	private Long tirId;
	
	@Column(name="reward_per")
	private String rewardPer;
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;

	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="org_type")
	private String orgType;
	
	@Column(name="start_amount")
	private double startAmount;
	
	@Column(name="end_amount")
	private double endAmount;
}
