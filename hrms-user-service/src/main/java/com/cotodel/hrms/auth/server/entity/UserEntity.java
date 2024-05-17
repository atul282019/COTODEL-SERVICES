package com.cotodel.hrms.auth.server.entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_details")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="user_details_id_seq" , sequenceName="user_details_id_seq", allocationSize=1)
public class UserEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_details_id_seq")
	private Long id ;
	
    private String first_name;
    private String last_name;
    private String dateofbirth ;
    private String  gender;
    private String contact_number;
    @Column(unique=true)
    private String email ;
    private String address ;
    private String org_type;
    private String  org_name;
    @Column(unique=true)
    private String  mobile ;
    private LocalDate  created_date ;
    private int  email_verify_status;
    private int  mobile_verify_status;
    private LocalDate email_verify_date;
    private LocalDate mobile_verify_date;
    private String username;
    private String pwd ;
    private int status ;
    private int employerid ;
    private int role_id ;
    
//    @OneToMany
//   	@JoinTable(name = "h_user_emp", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "user"))
//    private List<UserEmpEntity> userEmpEntity ;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserEmpEntity> userEmpEntity;
   // @OneToMany
//    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="user")
//    private List<UserEmpEntity> userEmpEntity ;
    
}
