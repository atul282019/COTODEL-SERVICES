package com.cotodel.hrms.auth.server.entity;

/**
 * @author vinay
 */
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
@Table(name="state_mst")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="state_mst_seq" , sequenceName="state_mst_seq", allocationSize=1)
public class StateMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="state_mst_seq")
	private Long id;
	
	private int state_code;
	private String state_name;
	private int status;
	private LocalDate created_date;
	private LocalDate  updated_date;

}
