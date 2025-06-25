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
@Table(name="band")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="band_seq" , sequenceName="band_seq", allocationSize=1)

public class BandEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="band_seq")	
	@Column(name="id")
	private Long id;
	
	
	@Column(name = "band_id")
    private String bandId;
	
}
