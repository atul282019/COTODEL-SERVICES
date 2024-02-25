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
@Table(name="sector_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="sector_master_seq" , sequenceName="sector_master_seq", allocationSize=1)
public class SectorMaster implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sector_master_seq")
	@Column(name="sectorid")
	private Long sectorId;
	
	@Column(name="sector_name")
	private String sectorName;

}
