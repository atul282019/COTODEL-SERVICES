package com.cotodel.hrms.auth.server.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="h_user_emp")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="h_user_empid_seq" , sequenceName="h_user_empid_seq", allocationSize=1)
public class UserEmpEntity implements Serializable{
	
	 	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="h_user_empid_seq")
	private Long id ;
  // private Long  user_id;
	private String employer_code;
	private Long employer_id ;
	private Integer status;
	private Long currentactive;
	private LocalDate created_date;
	private String created_by ;
    private LocalDate updated_date;
    private String  updated_by;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userDetails;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity user;

}
