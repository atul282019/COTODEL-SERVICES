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
@Table(name="licence_mst")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="licence_mst_seq" , sequenceName="licence_mst_seq", allocationSize=1)
public class LicenceMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="licence_mst_seq")
	private Long id;
    private String licence_key ;
    private LocalDate valid_form;
    private LocalDate valid_to;
    private int  status ;
    private String created_by;
    private LocalDate created_date;
    private String updated_by ;
    private LocalDate updated_date;
    private String enc_key;
    private int employer_id;

}
