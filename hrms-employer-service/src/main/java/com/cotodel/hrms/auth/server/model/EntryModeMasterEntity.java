package com.cotodel.hrms.auth.server.model;

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
@Table(name="entrymode_master")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="entrymode_master_seq" , sequenceName="entrymode_master_seq", allocationSize=1)
public class EntryModeMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="entrymode_master_seq")
	@Column(name="id_pk")
	private Long id;

	@Column(name="mode_desc", length=19)
	private String modeDesc;//Single/Bulk
	
	@Column(name="status")
	private int status;//status	int	for flag value			
}
